package demidova.materialdesign.viewmodel


import com.example.nasaapp.model.data.EarthEpicServerResponseData
import demidova.materialdesign.repository.PictureOfTheDayResponseData
import demidova.materialdesign.repository.mars.MarsPhotosServerResponseData

sealed class PhotosPlanetsState {
    data class SuccessPOD(val serverResponseData: PictureOfTheDayResponseData) :
        PhotosPlanetsState()

    data class SuccessEarthEpic(val serverResponseData: List<EarthEpicServerResponseData>) :
        PhotosPlanetsState()

    data class SuccessMars(val serverResponseData: MarsPhotosServerResponseData) :
        PhotosPlanetsState()

    data class Error(val error: Throwable) : PhotosPlanetsState()
    object Loading : PhotosPlanetsState()
}
