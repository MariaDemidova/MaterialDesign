package demidova.materialdesign.view.recycle

import android.view.View
import androidx.appcompat.widget.AppCompatImageView

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}

interface ItemTouchHelperViewHolder {
    fun onItemSelected()
    fun onItemClear()
}