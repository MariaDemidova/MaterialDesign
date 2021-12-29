package demidova.materialdesign.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasaapp.model.data.EarthEpicServerResponseData
import demidova.materialdesign.BuildConfig
import demidova.materialdesign.repository.PhotosRetrofitImpl
import demidova.materialdesign.repository.mars.MarsPhotosServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

const val TODAY = 0

class ApiActivityViewModel(
    private val liveDataToObserve: MutableLiveData<PhotosPlanetsState> = MutableLiveData(),
    private val retrofitImpl: PhotosRetrofitImpl = PhotosRetrofitImpl()
) : ViewModel() {

    fun getData(): LiveData<PhotosPlanetsState> {
        return liveDataToObserve
    }

    fun getMarsPicture() {
        liveDataToObserve.postValue(PhotosPlanetsState.Loading)
        val earthDate = takeDate(TODAY)
        retrofitImpl.getMarsPictureByDate(earthDate, BuildConfig.NASA_API_KEY, marsCallback)
    }

    private fun takeDate(count: Int): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, count)
        var format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format1.format(currentDate.time)
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

    fun getEpic() {
        liveDataToObserve.postValue(PhotosPlanetsState.Loading)
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PhotosPlanetsState.Error(Throwable("API_ERROR"))
        } else {
            retrofitImpl.getEPIC(apiKey, epicCallback)
        }
    }

    private val epicCallback = object : Callback<List<EarthEpicServerResponseData>> {

        override fun onResponse(
            call: Call<List<EarthEpicServerResponseData>>,
            response: Response<List<EarthEpicServerResponseData>>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.postValue(PhotosPlanetsState.SuccessEarthEpic(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.postValue(PhotosPlanetsState.Error(Throwable("UNKNOWN_ERROR")))
                } else {
                    liveDataToObserve.postValue(PhotosPlanetsState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<List<EarthEpicServerResponseData>>, t: Throwable) {
            liveDataToObserve.postValue(PhotosPlanetsState.Error(t))
        }
    }

}