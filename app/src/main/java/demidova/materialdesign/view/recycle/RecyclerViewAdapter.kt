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


    fun appendItem() {
        noteDataList.add(generateItem())
        notifyItemInserted(itemCount - 1)
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
                titleTextView.text = data.first.title

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
                    moveDown()
                }
                moveItemUp.setOnClickListener {
                    moveUp()
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

        private fun moveUp() {
            if (layoutPosition == 0) {
                noteDataList.removeAt(layoutPosition).apply {
                    noteDataList.add(itemCount , this)
                    notifyItemMoved(layoutPosition, itemCount - 1)
                }
            } else {
                noteDataList.removeAt(layoutPosition).apply {
                    noteDataList.add(layoutPosition - 1, this)
                    notifyItemMoved(layoutPosition,layoutPosition - 1)
                }
            }
        }

        private fun moveDown() {
            if (layoutPosition == itemCount-1) {
                noteDataList.removeAt(layoutPosition).apply {
                    noteDataList.add(0, this)
                    notifyItemMoved(layoutPosition, 0)
                }
            }else {
                noteDataList.removeAt(layoutPosition).apply {
                    noteDataList.add(layoutPosition + 1, this)
                    notifyItemMoved(layoutPosition, layoutPosition + 1)
                }
            }
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
                titleTextView.text = data.first.title

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
                    moveDown( )
                }
                moveItemUp.setOnClickListener {
                    moveUp( )
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

        private fun moveUp() {
            if (layoutPosition == 0) {
                noteDataList.removeAt(layoutPosition).apply {
                    noteDataList.add(itemCount, this)
                    notifyItemMoved(layoutPosition, itemCount - 1)
                }
            } else {
                noteDataList.removeAt(layoutPosition).apply {
                    noteDataList.add(layoutPosition - 1, this)
                    notifyItemMoved(layoutPosition,layoutPosition - 1)
                }
            }
        }

        private fun moveDown() {
            if (layoutPosition == itemCount-1) {
                noteDataList.removeAt(layoutPosition).apply {
                    noteDataList.add(0, this)
                    notifyItemMoved(layoutPosition, 0)
                }
            }else {
                noteDataList.removeAt(layoutPosition).apply {
                    noteDataList.add(layoutPosition + 1, this)
                    notifyItemMoved(layoutPosition, layoutPosition + 1)
                }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.GREEN)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.parseColor("#99858080"))
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        noteDataList.removeAt(fromPosition).apply {
            noteDataList.add(toPosition, this)
        }
        notifyItemMoved(fromPosition,toPosition)
    }

    override fun onItemDismiss(position: Int) {
        noteDataList.removeAt(position)
        notifyItemRemoved(position)
    }
}