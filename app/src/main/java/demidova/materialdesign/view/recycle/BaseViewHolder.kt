package demidova.materialdesign.view.recycle

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import demidova.materialdesign.model.NoteData

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(data: Pair<NoteData,Boolean>)
}