package demidova.materialdesign.repository

import com.google.gson.GsonBuilder
import demidova.materialdesign.repository.OkHTTPClient.getOkHTTPBuilderWithHeaders
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PictureOfTheDayRetrofitImpl {
    private val baseUrl = "https://api.nasa.gov/"
    fun getRetrofitImpl():PictureOfTheDayAPI{
        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(getOkHTTPBuilderWithHeaders())
            .build()
        return retrofit.create(PictureOfTheDayAPI::class.java)
    }


}