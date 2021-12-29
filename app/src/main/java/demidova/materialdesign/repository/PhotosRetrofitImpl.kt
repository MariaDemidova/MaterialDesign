package demidova.materialdesign.repository

import com.example.nasaapp.model.data.EarthEpicServerResponseData
import com.google.gson.GsonBuilder
import demidova.materialdesign.repository.OkHTTPClient.getOkHTTPBuilderWithHeaders
import demidova.materialdesign.repository.mars.MarsPhotosServerResponseData
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhotosRetrofitImpl {
    private val baseUrl = "https://api.nasa.gov/"
    fun getRetrofitImpl(): PhotosAPI {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(getOkHTTPBuilderWithHeaders())
            .build()
        return retrofit.create(PhotosAPI::class.java)
    }

    private val api by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(getOkHTTPBuilderWithHeaders())
            .build()
            .create(PhotosAPI::class.java)
    }

    fun getMarsPictureByDate(
        earth_date: String,
        apiKey: String,
        marsCallbackByDate: Callback<MarsPhotosServerResponseData>
    ) {
        api.getMarsImageByDate(earth_date, apiKey).enqueue(marsCallbackByDate)
    }

    fun getEPIC(apiKey: String, epicCallback: Callback<List<EarthEpicServerResponseData>>) {
        api.getEPIC(apiKey).enqueue(epicCallback)
    }

}