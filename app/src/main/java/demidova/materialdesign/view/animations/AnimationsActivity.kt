package demidova.materialdesign.view.animations

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import androidx.transition.Fade.IN
import demidova.materialdesign.R


import demidova.materialdesign.databinding.ActivityAnimationsBinding


class AnimationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationsBinding
    private var isExpand = false
    private var isClick = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.adapter = Adapter()

        binding.imageView.setOnClickListener {

            isExpand = !isExpand

            val params = binding.imageView.layoutParams as FrameLayout.LayoutParams

            val transitionSet = TransitionSet()
            val transitionCB = ChangeBounds()
            val transitionImage = ChangeImageTransform()
            transitionCB.duration = 2000
            transitionImage.duration = 2000
            transitionSet.addTransition(transitionCB)
            transitionSet.addTransition(transitionImage)
            TransitionManager.beginDelayedTransition(binding.container,transitionSet)

            if (isExpand) {
                binding.imageView.scaleType = ImageView.ScaleType.CENTER
               // params.height = FrameLayout.LayoutParams.MATCH_PARENT
            } else {
                binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                params.height = FrameLayout.LayoutParams.WRAP_CONTENT
            }
            binding.imageView.layoutParams = params
        }
    }

    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_animations_recycler_item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                isClick = !isClick
                val button = it as ImageButton

                val explodeTransition = Explode()
                val fadeTransition = Fade()
                val setTransition = TransitionSet()
                val fadeTransitionImage = Fade(IN)

                explodeTransition.duration = 3000
                fadeTransition.duration = 3000
                fadeTransitionImage.duration = 3000

                explodeTransition.excludeTarget(button, true)

                explodeTransition.epicenterCallback = object : Transition.EpicenterCallback() {
                    override fun onGetEpicenter(transition: Transition): Rect {
                        val rect = Rect(
                            button.x.toInt(),
                            button.y.toInt(),
                            button.x.toInt(),
                            button.y.toInt()
                        )
                        return rect
                    }
                }

                setTransition.addTransition(fadeTransition)
                setTransition.addTransition(explodeTransition)

                TransitionManager.beginDelayedTransition(binding.container, fadeTransitionImage)
                binding.imageView.visibility = View.VISIBLE
                TransitionManager.beginDelayedTransition(binding.recyclerView, setTransition)
                binding.recyclerView.adapter = null
               // binding.recyclerView.visibility = View.GONE
            }
        }

        override fun getItemCount(): Int {
            return 32
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}