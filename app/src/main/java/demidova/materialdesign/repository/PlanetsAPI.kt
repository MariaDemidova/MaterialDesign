package demidova.materialdesign.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanetsAPI {
    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsImageByDate(
        @Query("earth_date") earth_date: String,
        @Query("api_key") apiKey: String,
    ): Call<MarsPhotosServerResponseData>
}