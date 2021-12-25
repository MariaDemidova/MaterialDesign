package demidova.materialdesign.viewmodel

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import demidova.materialdesign.BuildConfig
import demidova.materialdesign.repository.MarsPhotosServerResponseData
import demidova.materialdesign.repository.PictureOfTheDayResponseData
import demidova.materialdesign.repository.PictureOfTheDayRetrofitImpl
import demidova.materialdesign.repository.PlanetsRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ApiActivityViewModel(
    private val liveDataToObserve: MutableLiveData<PhotosPlanetsState> = MutableLiveData(),
    private val retrofitImpl: PlanetsRetrofitImpl = PlanetsRetrofitImpl()
) : ViewModel() {

    fun getData(): LiveData<PhotosPlanetsState> {
        return liveDataToObserve
    }

    fun getMarsPicture() {
        liveDataToObserve.postValue(PhotosPlanetsState.Loading)
        val earthDate = getDayBeforeYesterday()
        retrofitImpl.getMarsPictureByDate(earthDate, BuildConfig.NASA_API_KEY, marsCallback)
    }

    fun getDayBeforeYesterday(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val yesterday = LocalDateTime.now().minusDays(1)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return yesterday.format(formatter)
        } else {
            val cal: Calendar = Calendar.getInstance()
            val s = SimpleDateFormat("yyyy-MM-dd")
            cal.add(Calendar.DAY_OF_YEAR, -1)
            return s.format(cal.time)
        }
    }

    val marsCallback = object : Callback<MarsPhotosServerResponseData> {

        override fun onResponse(
            call: Call<MarsPhotosServerResponseData>,
            response: Response<MarsPhotosServerResponseData>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.postValue(PhotosPlanetsState.SuccessMars(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.postValue(PhotosPlanetsState.Error(Throwable("UNKNOWN_ERROR")))
                } else {
                    liveDataToObserve.postValue(PhotosPlanetsState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<MarsPhotosServerResponseData>, t: Throwable) {
            liveDataToObserve.postValue(PhotosPlanetsState.Error(t))
        }
    }

}