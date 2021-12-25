package demidova.materialdesign.repository

import com.example.nasaapp.model.data.EarthEpicServerResponseData
import com.example.nasaapp.model.data.SputnikServerResponseData
import demidova.materialdesign.repository.mars.MarsPhotosServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PictureOfTheDayResponseData>

    @GET("planetary/apod")
    fun getPotdByYesterdey(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Call<PictureOfTheDayResponseData>

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsImageByDate(
        @Query("earth_date") earth_date: String,
        @Query("api_key") apiKey: String,
    ): Call<MarsPhotosServerResponseData>

    @GET("/planetary/earth/assets")
    fun getLandscapeImageFromSputnik(
        @Query("lon") lon: Float,
        @Query("lat") lat: Float,
        @Query("date") dateString: String,
        @Query("dim") dim: Float,
        @Query("api_key") apiKey: String
    ): Call<SputnikServerResponseData>

    @GET("EPIC/api/natural")
    fun getEPIC(
        @Query("api_key") apiKey: String,
    ): Call<List<EarthEpicServerResponseData>>
}