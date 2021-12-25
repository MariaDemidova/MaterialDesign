package demidova.materialdesign.repository


import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlanetsRetrofitImpl {

    companion object {
        private const val BASE_URL = "https://api.nasa.gov/"
    }

    private val api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(OkHTTPClient.getOkHTTPBuilderWithHeaders())
            .build()
            .create(PlanetsAPI::class.java)
    }

    fun getMarsPictureByDate(
        earth_date: String,
        apiKey: String,
        marsCallbackByDate: Callback<MarsPhotosServerResponseData>
    ) {
        api.getMarsImageByDate(earth_date, apiKey).enqueue(marsCallbackByDate)
    }
}
