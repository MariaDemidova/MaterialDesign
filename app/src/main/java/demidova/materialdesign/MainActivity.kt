package demidova.materialdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import demidova.materialdesign.view.PictureOfTheDayFragment
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.container,
                PictureOfTheDayFragment.newInstance()).commit()
        }
    }
}