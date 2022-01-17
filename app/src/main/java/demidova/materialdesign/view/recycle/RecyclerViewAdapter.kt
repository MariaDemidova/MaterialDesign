package demidova.materialdesign.view.recycle

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView

import demidova.materialdesign.databinding.ActivityRecyclerItemCatsBinding
import demidova.materialdesign.databinding.ActivityRecyclerItemDogsBinding
import demidova.materialdesign.model.NoteData
import demidova.materialdesign.model.TYPE_CATS
import demidova.materialdesign.model.TYPE_DOGS
import demidova.materialdesign.utils.MyCallback

class RecyclerViewAdapter(
    private val noteDataList: MutableList<Pair<NoteData, Boolean>>,
    private val callbackListener: MyCallback
) : RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {
    lateinit  var arrowUp: AppCompatImageView
    lateinit  var arrowDown: AppCompatImageView

    fun appendItem() {

        noteDataList.add(generateItem())
       // arrowDown.visibility =View.GONE
       // notifyItemInserted(itemCount - 1)
        notifyDataSetChanged()
    }

    private fun generateItem(): Pair<NoteData, Boolean> {
        return NoteData(title = "NewNote", type = 0) to false
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_DOGS -> {
                val bindingViewHolder = ActivityRecyclerItemDogsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DogsViewHolder(bindingViewHolder.root)
            }
            TYPE_CATS -> {
                val bindingViewHolder = ActivityRecyclerItemCatsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CatsViewHolder(bindingViewHolder.root)
            }
            else -> {
                val bindingViewHolder = ActivityRecyclerItemDogsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DogsViewHolder(bindingViewHolder.root)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return noteDataList[position].first.type
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(noteDataList[position])
    }

    override fun getItemCount(): Int {
        return noteDataList.size
    }

    inner class DogsViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {

        override fun bind(data: Pair<NoteData, Boolean>) {
            ActivityRecyclerItemDogsBinding.bind(itemView).apply {
                  arrowUp = moveItemUp
                  arrowDown = moveItemDown

                titleTextView.text = data.first.title

                when (layoutPosition) {
                    0 -> moveItemUp.visibility = View.GONE
                    (itemCount - 1) -> moveItemDown.visibility = View.GONE

                }

//                for (i in 1..(layoutPosition - 2)) {
//                    moveItemUp.visibility = View.VISIBLE
//                    moveItemDown.visibility = View.VISIBLE
//                }

                noteImageView.setOnClickListener {
                    callbackListener.onClick(layoutPosition)
                }
                addItemImageView.setOnClickListener {
                    addItemToPosition()
                }
                removeItemImageView.setOnClickListener {
                    removeItem()
                }
                moveItemDown.setOnClickListener {
                    moveDown(arrowDown)
                }
                moveItemUp.setOnClickListener {
                    moveUp(arrowUp)
                }
                DescriptionTextView.visibility = if (data.second) View.VISIBLE else View.GONE

                titleTextView.setOnClickListener {
                    toggleDescription()
                }
            }
        }

        private fun addItemToPosition() {
            noteDataList.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)

        }

        private fun removeItem() {
            noteDataList.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun toggleDescription() {
            noteDataList[layoutPosition] = noteDataList[layoutPosition].run {
                first to !second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun moveUp(arrowUp: AppCompatImageView) {
            if (layoutPosition == 0) {
                arrowUp.visibility = View.GONE
            } else {
                arrowUp.visibility = View.VISIBLE
                noteDataList.removeAt(layoutPosition).apply {
                    noteDataList.add(layoutPosition - 1, this)
                }
            }

            // notifyItemMoved(layoutPosition, layoutPosition - 1)

            notifyDataSetChanged()
        }

        private fun moveDown(arrowDown: AppCompatImageView) {

            if (layoutPosition == noteDataList.size - 1) {
                arrowDown.visibility = View.GONE
            } else {
                noteDataList.removeAt(layoutPosition).apply {
                    noteDataList.add(layoutPosition + 1, this)
                    arrowDown.visibility = View.VISIBLE
                }
            }
            /// notifyItemMoved(layoutPosition, layoutPosition + 1)
            notifyDataSetChanged()

        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.GREEN)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.parseColor("#99858080"))
        }
    }

    inner class CatsViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {
        override fun bind(data: Pair<NoteData, Boolean>) {
            ActivityRecyclerItemCatsBinding.bind(itemView).apply {
                  arrowUp = moveItemUp
                  arrowDown = moveItemDown

                titleTextView.text = data.first.title

                when (layoutPosition) {
                    0 -> moveItemUp.visibility = View.GONE
                    (itemCount - 1) -> moveItemDown.visibility = View.GONE
                }

                for (i in 1..(layoutPosition - 2)) {
                    moveItemUp.visibility = View.VISIBLE
                    moveItemDown.visibility = View.VISIBLE
                }

                noteImageView.setOnClickListener {
                    callbackListener.onClick(layoutPosition)
                }
                addItemImageView.setOnClickListener {
                    addItemToPosition()
                }
                removeItemImageView.setOnClickListener {
                    removeItem()
                }
                moveItemDown.setOnClickListener {
                    moveDown(arrowDown)
                }
                moveItemUp.setOnClickListener {
                    moveUp(arrowUp)
                }
                DescriptionTextView.visibility = if (data.second) View.VISIBLE else View.GONE

                titleTextView.setOnClickListener {
                    toggleDescription()
                }
            }
        }

        private fun addItemToPosition() {
            noteDataList.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            noteDataList.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun toggleDescription() {
            noteDataList[layoutPosition] = noteDataList[layoutPosition].run {
                first to !second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun moveUp(arrowUp: AppCompatImageView) {
            if (layoutPosition == 0) {
                arrowUp.visibility = View.GONE
            } else {
                arrowUp.visibility = View.VISIBLE
                noteDataList.removeAt(layoutPosition).apply {
                    noteDataList.add(layoutPosition - 1, this)
                }
            }

            // notifyItemMoved(layoutPosition, layoutPosition - 1)

            notifyDataSetChanged()
        }

        private fun moveDown(arrowDown: AppCompatImageView) {

            if (layoutPosition == noteDataList.size - 1) {
                arrowDown.visibility = View.GONE
            } else {
                noteDataList.removeAt(layoutPosition).apply {
                    noteDataList.add(layoutPosition + 1, this)
                    arrowDown.visibility = View.VISIBLE
                }
            }
            /// notifyItemMoved(layoutPosition, layoutPosition + 1)
            notifyDataSetChanged()

        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.GREEN)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.parseColor("#99858080"))
        }
    }

    override fun onItemMove(
        fromPosition: Int,
        toPosition: Int,
        arrowUp: AppCompatImageView,
        arrowDown: AppCompatImageView
    ) {

        when (toPosition) {
            0 -> arrowUp.visibility = View.GONE
            noteDataList.size - 1 -> arrowDown.visibility = View.GONE
        }

        noteDataList.removeAt(fromPosition).apply {
            noteDataList.add(toPosition, this)
        }
       // notifyItemMoved(fromPosition, toPosition)
        notifyDataSetChanged()
    }

    override fun onItemDismiss(position: Int) {
        noteDataList.removeAt(position)
        notifyItemRemoved(position)
    }
}