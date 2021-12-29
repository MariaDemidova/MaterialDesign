package demidova.materialdesign.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import demidova.materialdesign.BuildConfig
import demidova.materialdesign.repository.PictureOfTheDayResponseData
import demidova.materialdesign.repository.PictureOfTheDayRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayState> = MutableLiveData(),
    private val retrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl()
) : ViewModel() {

    fun getData(): LiveData<PictureOfTheDayState> {
        return liveDataForViewToObserve
    }

    fun sendServerRequest() {

        liveDataForViewToObserve.value = PictureOfTheDayState.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = PictureOfTheDayState.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(callback)
        }
    }

    fun sendServerRequestByYesterday() {
        liveDataForViewToObserve.value = PictureOfTheDayState.Loading(0)

        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = PictureOfTheDayState.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPotdByYesterdey(apiKey, takeDate(-1))
                .enqueue(callback)
        }
    }

    fun sendServerRequestByTwoDaysAgo() {
        liveDataForViewToObserve.value = PictureOfTheDayState.Loading(0)

        val currentDate = Calendar.getInstance()
        currentDate.set(Calendar.DAY_OF_MONTH, -2)
        Log.d("gopa", currentDate.time.toString())

        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = PictureOfTheDayState.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPotdByYesterdey(apiKey, takeDate(-2))
                .enqueue(callback)
        }
    }

    private val callback = object : Callback<PictureOfTheDayResponseData> {
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserve.value = PictureOfTheDayState.Success(response.body()!!)
            } else {
                Log.d("PictureOfTheDay Fragment: ", "ошибка в onResponse")
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            Log.d("PictureOfTheDay Fragment: ", "onFailure")
        }
    }

    private fun takeDate(count: Int): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, count)
        Log.d("gopa", currentDate.time.toString())
        var format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format1.format(currentDate.time)
    }
}