package demidova.materialdesign.view.univers

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        _binding = FragmentMarsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner,{ render(it) })
        viewModel.getMarsPicture()

    }

    private fun render(appState: PhotosPlanetsState) {

        when(appState){
            is PhotosPlanetsState.Error ->
                Snackbar.make(binding.root, appState.error.toString(), Snackbar.LENGTH_SHORT).show()
            is PhotosPlanetsState.Loading -> {

                binding.imageView.load(R.drawable.ic_no_photo_vector)
            }

            is PhotosPlanetsState.SuccessMars -> {
                var url = ""
//                appState.serverResponseData.photos.forEachIndexed { index, marsServerResponseData ->
//                    url = marsServerResponseData.imgSrc.toString()
//                }

                if (appState.serverResponseData.photos.isNotEmpty()) {
                    url = appState.serverResponseData.photos.first().imgSrc.toString()

                }
                if (url.isNotEmpty()) {

                    binding.imageView.load(url)
                } else {
                    binding.videoOfTheDay.visibility = View.VISIBLE
                    binding.videoOfTheDay.text = "Фотки сегодня не будет. Видео не покажу"
                }
            }
        }
    }
}