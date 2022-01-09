package demidova.materialdesign.view.animations

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.*
import demidova.materialdesign.databinding.ActivityAnimationsBinding

class AnimationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationsBinding
    private var isTextViewVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            isTextViewVisible = !isTextViewVisible
           // val transition = Slide(Gravity.BOTTOM)
            val transition = TransitionSet()
            transition.duration = 2000
            transition.addTransition(Fade())
            transition.addTransition(ChangeBounds())
            TransitionManager.beginDelayedTransition(binding.transitionsContainer, transition)
            binding.text.visibility = if(isTextViewVisible) View.VISIBLE else View.GONE

        }
    }
}