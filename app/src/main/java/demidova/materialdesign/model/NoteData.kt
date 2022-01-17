package demidova.materialdesign.model


const val TYPE_DOGS =0
const val TYPE_CATS =1
data class NoteData(val title: String = "NONE", val text: String = "EMPTY", val type:Int=TYPE_DOGS) {
}