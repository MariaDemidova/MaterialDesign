package demidova.materialdesign.view.univers

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.snackbar.Snackbar
import demidova.materialdesign.R
import demidova.materialdesign.databinding.FragmentMarsBinding
import demidova.materialdesign.viewmodel.ApiActivityViewModel
import demidova.materialdesign.viewmodel.PhotosPlanetsState


class MarsFragment:Fragment() {

    private var _binding: FragmentMarsBinding? = null
    private val binding: FragmentMarsBinding
        get() {
            return _binding!!
        }

//    lateinit var ApiActivityViewModel: ApiActivityViewModel

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        ApiActivityViewModel = (context as ApiActivity).ApiActivityViewModel
//    }

    private lateinit var parentActivity: ApiActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity =
            requireActivity() as ApiActivity
    }

    private val viewModel: ApiActivityViewModel by lazy {
        ViewModelProvider(this).get(ApiActivityViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("gopa", "createMars")
        _binding = FragmentMarsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("gopa", "onViewCreated")
        viewModel.getData().observe(viewLifecycleOwner,{ render(it) })
        viewModel.getMarsPicture()

    }

    private fun render(appState: PhotosPlanetsState) {
        Log.d("gopa", "render")
        when(appState){
            is PhotosPlanetsState.Error ->
                Snackbar.make(binding.root, appState.error.toString(), Snackbar.LENGTH_SHORT).show()
            is PhotosPlanetsState.Loading -> {
                Log.d("gopa", ".Loading")
                binding.imageView.load(R.drawable.ic_no_photo_vector)
            }

            is PhotosPlanetsState.SuccessMars -> {
                Log.d("gopa", "SuccessMars")
                var url = ""
                appState.serverResponseData.photos.forEachIndexed { index, marsServerResponseData ->
                    url = marsServerResponseData.imgSrc.toString()
                }

                if (appState.serverResponseData.photos.isNotEmpty()) {
                    appState.serverResponseData.photos.first().imgSrc
                }
//                val url = appState.serverResponseData.photos.first().imgSrc
                if (url != null) {

                    binding.imageView.load(url)
                } else{
                    binding.videoOfTheDay.text = "Сегодня у нас без картинки дня, но есть  видео дня! " +
                            "${url.toString()} \n кликни >ЗДЕСЬ< чтобы открыть в новом окне"
                }
            }
        }
    }

//    private fun setData(data: PhotosPlanetsState.SuccessMars)  {
//        val url = data.serverResponseData.hdurl
//        if (url.isNullOrEmpty()) {
//            val videoUrl = data.serverResponseData.url
//            videoUrl?.let { showAVideoUrl(it) }
//        } else {
//            binding.imageView.load(url)
//        }
//    }

    private fun showAVideoUrl(videoUrl: String) = with(binding) {
       imageView.visibility = View.GONE
      //  videoOfTheDay.visibility = View.VISIBLE
        videoOfTheDay.text = "Сегодня у нас без картинки дня, но есть  видео дня! " +
                "${videoUrl.toString()} \n кликни >ЗДЕСЬ< чтобы открыть в новом окне"
//        videoOfTheDay.setOnClickListener {
//            val i = Intent(Intent.ACTION_VIEW).apply {
//                data = Uri.parse(videoUrl)
//            }
//            startActivity(i)
//        }
    }
}