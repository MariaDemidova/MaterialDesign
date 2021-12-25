package demidova.materialdesign.view.api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.badge.BadgeDrawable
import demidova.materialdesign.R
import demidova.materialdesign.databinding.ActivityBottomApiBinding

class ApiBottomActivity : AppCompatActivity() {
    lateinit var binding: ActivityBottomApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_view_questions -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, QuestionFragment()).commit()
                    true
                }
                R.id.bottom_view_think -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ThinkFragment()).commit()
                    true
                }
                R.id.bottom_view_done -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, DoneFragment()).commit()
                    true
                }
                else -> {
                    false
                }
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.bottom_view_questions
        with(binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_think)) {
            number = 5
            badgeGravity = BadgeDrawable.TOP_END
            maxCharacterCount = 3
        }

    }


}