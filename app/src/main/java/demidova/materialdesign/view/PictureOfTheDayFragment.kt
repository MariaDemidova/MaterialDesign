package demidova.materialdesign.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import demidova.materialdesign.MainActivity
import demidova.materialdesign.R
import demidova.materialdesign.databinding.FragmentMainBinding
import demidova.materialdesign.databinding.FragmentMainStartBinding
import demidova.materialdesign.view.univers.ApiActivity
import demidova.materialdesign.view.api.ApiBottomActivity
import demidova.materialdesign.view.chips.SettingsFragment
import demidova.materialdesign.viewmodel.PictureOfTheDayState
import demidova.materialdesign.viewmodel.PictureOfTheDayViewModel
import java.util.*


const val ThemeOne = 1
const val ThemeSecond = 2
const val ThemeUsual = 3

class PictureOfTheDayFragment() : Fragment() {

    private var _binding: FragmentMainStartBinding? = null
    val binding: FragmentMainStartBinding
        get() {
            return _binding!!

        }
    private val KEY_SP = "sp"
    private val KEY_TAB_POSITION = "tab_position"

    private var isMain = true

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }


    private lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity =
            requireActivity() as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendServerRequest()

        binding.tabs.getTabAt(getTabPosition())?.select()
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        parentActivity.setCurrentTheme(ThemeOne)
                        setTabPosition(0)
                        parentActivity.recreate()

                    }
                    1 -> {
                        parentActivity.setCurrentTheme(ThemeSecond)
                        setTabPosition(1)
                        parentActivity.recreate()
                    }
                    2 -> {
                        parentActivity.setCurrentTheme(ThemeUsual)
                        setTabPosition(2)
                        parentActivity.recreate()
                    }
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }
        })

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        binding.yesterdayBtn.setOnClickListener {
            viewModel.getData().observe(viewLifecycleOwner, Observer {
                renderData(it)
            })
            viewModel.sendServerRequestByYesterday()
        }

        binding.todayBtn.setOnClickListener {
            viewModel.getData().observe(viewLifecycleOwner, Observer {
                renderData(it)
            })
            viewModel.sendServerRequest()
        }

        binding.twoDaysAgoBtn.setOnClickListener {
            viewModel.getData().observe(viewLifecycleOwner, Observer {
                renderData(it)
            })
            viewModel.sendServerRequestByTwoDaysAgo()
        }

        val behavior = BottomSheetBehavior.from(binding.includeBottomSheet.bottomSheetContainer)

            behavior.state = BottomSheetBehavior.STATE_EXPANDED


        behavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {

                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("mylogs", "$slideOffset slideOffset")

            }
        })

        setBottomAppBar()
    }

    private fun renderData(state: PictureOfTheDayState) {
        when (state) {
            is PictureOfTheDayState.Error -> {
                state.error.message
            }
            is PictureOfTheDayState.Loading -> {
                binding.imageView.load(R.drawable.ic_no_photo_vector)
            }
            is PictureOfTheDayState.Success -> {
                val pictureOfTheDayResponseData = state.pictureOfTheDayResponseData
                val url = pictureOfTheDayResponseData.url
                binding.imageView.load(url) {
                    lifecycle(this@PictureOfTheDayFragment)
                    error(R.drawable.ic_load_error_vector)
                    placeholder(R.drawable.ic_no_photo_vector)
                }
                binding.includeBottomSheet.bottomSheetDescriptionHeader.text =
                    pictureOfTheDayResponseData.title
                binding.includeBottomSheet.bottomSheetDescription.text =
                    pictureOfTheDayResponseData.explanation
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.api_activity -> {
                startActivity(Intent(requireContext(), ApiActivity::class.java))
            }
            R.id.api_bottom_activity -> {
                startActivity(Intent(requireContext(), ApiBottomActivity::class.java))
            }
            R.id.app_bar_settings -> requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    SettingsFragment.newInstance()
                ).commit()
            android.R.id.home -> BottomNavigationDrawerFragment().show(
                requireActivity().supportFragmentManager,
                ""
            )
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setBottomAppBar() {

        val context = activity as MainActivity
        context.setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)

        binding.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG, "Fragment back pressed invoked")
                    if (isMain) {
                        System.exit(0)
                    } else {
                        isMain = true
                        binding.bottomAppBar.navigationIcon = ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_hamburger_menu_bottom_bar
                        )
                        binding.bottomAppBar.fabAlignmentMode =
                            BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                        binding.fab.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_plus_fab
                            )
                        )
                        binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
                    }
                }
            }
            )
    }

    companion object {
        fun newInstance(): PictureOfTheDayFragment {
            return PictureOfTheDayFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun setTabPosition(currentTheme: Int) {
        val sharedPreferences =
            requireContext().getSharedPreferences(KEY_SP, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_TAB_POSITION, currentTheme)
        editor.apply()
    }

    fun getTabPosition(): Int {
        val sharedPreferences =
            requireContext().getSharedPreferences(KEY_SP, AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_TAB_POSITION, 0)
    }

}