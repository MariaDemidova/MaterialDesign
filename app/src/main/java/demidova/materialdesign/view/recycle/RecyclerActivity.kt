package demidova.materialdesign.view.recycle

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import demidova.materialdesign.databinding.ActivityRecyclerBinding
import demidova.materialdesign.model.NoteData
import demidova.materialdesign.utils.MyCallback

class RecyclerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = arrayListOf(
            NoteData("Cats", type = 1) to false,
            NoteData("Cats", type = 1) to false,
            NoteData("Dogs", type = 0) to false,
        )

        val adapter = RecyclerViewAdapter(data,
            object : MyCallback {
                override fun onClick(position: Int) {
                    Toast.makeText(
                        this@RecyclerActivity, "РАБОТАЕТ ${data[position].first.title}}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        binding.recyclerView.adapter = adapter
        binding.recyclerActivityFAB.setOnClickListener {
            adapter.appendItem()
           // adapter.notifyDataSetChanged()
        }
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)
    }
}