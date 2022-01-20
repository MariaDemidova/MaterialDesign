package demidova.materialdesign

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import demidova.materialdesign.databinding.ActivityMainBinding
import demidova.materialdesign.view.PictureOfTheDayFragment


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}