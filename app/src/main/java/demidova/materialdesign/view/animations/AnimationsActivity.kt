package demidova.materialdesign.view.animations

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import androidx.transition.Fade.IN
import demidova.materialdesign.R
import demidova.materialdesign.databinding.ActivityAnimationsBinding

private const val duration = 5000L

class AnimationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationsBinding
    private var isExpand = false
    private var isDirectionRight = false
    private val smaller = "Меньше"
    private val bigger = "Больше"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.adapter = Adapter()

        val titles: MutableList<String> = ArrayList()
        for (i in 0..5) {
            titles.add("Item $i")
        }

        binding.buttonTransitions.setOnClickListener {
            binding.transitionsContainer.animate()
                .alpha(1f)
                .setDuration(3000)
            val cb = ChangeBounds()
            cb.duration = 2000
            TransitionManager.beginDelayedTransition(binding.transitionsContainer, cb)
            titles.shuffle()

            binding.transitionsContainer.removeAllViews()
            titles.forEach {
                binding.transitionsContainer.addView(ImageView(this).apply {

                    setBackgroundResource(R.drawable.btn_moon)
                    ViewCompat.setTransitionName(this, it)
                    setOnClickListener {
                        visibility = View.GONE
                    }
                })
            }
            binding.imageView.animate()
                .alpha(0.4f)
                .setDuration(3000)
        }

        binding.btnMove.setOnClickListener {
            changePictureSize()
            moveButton()

        }

        binding.imageView.setOnClickListener {
            changePictureSize()
            moveButton()
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
                binding.btnMove.visibility = View.VISIBLE
                binding.buttonTransitions.visibility = View.VISIBLE
                TransitionManager.beginDelayedTransition(binding.recyclerView, setTransition)

                binding.recyclerView.adapter = null
                ObjectAnimator.ofFloat(it, "rotation", 0f, 540f)
                    .setDuration(duration).start()

                ObjectAnimator.ofFloat(it, "scaleX", it.scaleX, 0f)
                    .setDuration(duration).start()

                ObjectAnimator.ofFloat(it, "scaleY", it.scaleY, 0f)
                    .setDuration(duration).start()
            }
        }

        override fun getItemCount(): Int {
            return 32
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private fun changePictureSize() {
        isExpand = !isExpand


        val params = binding.imageView.layoutParams as FrameLayout.LayoutParams
        val transitionSet = TransitionSet()
        val transitionCB = ChangeBounds()
        val transitionImage = ChangeImageTransform()
        transitionCB.duration = 2000
        transitionImage.duration = 2000
        transitionSet.addTransition(transitionCB)
        transitionSet.addTransition(transitionImage)
        TransitionManager.beginDelayedTransition(binding.container, transitionSet)

        if (isExpand) {
            binding.imageView.scaleType = ImageView.ScaleType.CENTER
        } else {
            binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            params.height = FrameLayout.LayoutParams.WRAP_CONTENT
        }
        binding.imageView.animate()
            .alpha(1f)
            .setDuration(3000)

        binding.transitionsContainer.animate()
            .alpha(0f)
            .setDuration(3000)

        binding.imageView.layoutParams = params
    }

    private fun moveButton() {
        isDirectionRight = !isDirectionRight

        binding.transitionsContainer.animate()
            .alpha(0f)
            .setDuration(3000)

        binding.imageView.animate()
            .alpha(1f)
            .setDuration(3000)

        val params = binding.btnMove.layoutParams as FrameLayout.LayoutParams

        val transition = ChangeBounds()
        transition.duration = 3000
        TransitionManager.beginDelayedTransition(binding.container, transition)

        params.gravity = if (isDirectionRight) {
            binding.btnMove.text = bigger

            Gravity.BOTTOM or Gravity.END
        } else {
            binding.btnMove.text = smaller

            Gravity.BOTTOM or Gravity.START
        }
        binding.btnMove.layoutParams = params
    }
}