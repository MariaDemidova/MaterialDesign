package demidova.materialdesign.view.univers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.snackbar.Snackbar
import demidova.materialdesign.BuildConfig
import demidova.materialdesign.R
import demidova.materialdesign.databinding.FragmentEarthBinding
import demidova.materialdesign.viewmodel.ApiActivityViewModel
import demidova.materialdesign.viewmodel.PhotosPlanetsState


class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding: FragmentEarthBinding
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
        _binding = FragmentEarthBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner, { render(it) })
        viewModel.getEpic()
    }

    private fun render(appState: PhotosPlanetsState) {

        when (appState) {
            is PhotosPlanetsState.Error ->
                Snackbar.make(binding.root, appState.error.toString(), Snackbar.LENGTH_SHORT).show()
            is PhotosPlanetsState.Loading -> {

                binding.imageView.load(R.drawable.ic_no_photo_vector)
            }

            is PhotosPlanetsState.SuccessEarthEpic -> {
                val strDate = appState.serverResponseData.last().date.split(" ").first()
                val image = appState.serverResponseData.last().image
                val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                        strDate.replace("-", "/", true) +
                        "/png/" +
                        "$image" +
                        ".png?api_key=${BuildConfig.NASA_API_KEY}"
                binding.imageView.load(url)
            }
        }
    }
}