package demidova.materialdesign.viewmodel


import demidova.materialdesign.repository.MarsPhotosServerResponseData
import demidova.materialdesign.repository.PictureOfTheDayResponseData

sealed class PhotosPlanetsState {
    data class SuccessPOD(val serverResponseData: PictureOfTheDayResponseData) : PhotosPlanetsState()
   // data class SuccessEarthEpic (val serverResponseData: List<EarthEpicServerResponseData>) : PhotosPlanetsState()
    data class SuccessMars(val serverResponseData: MarsPhotosServerResponseData) : PhotosPlanetsState()
    data class Error(val error: Throwable) : PhotosPlanetsState()
    object Loading : PhotosPlanetsState()
}
