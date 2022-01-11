package demidova.materialdesign.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import demidova.materialdesign.R
import demidova.materialdesign.databinding.BottomNavigationLayoutBinding
import demidova.materialdesign.view.animations.AnimationsActivity
import demidova.materialdesign.view.constraint.ConstraintFragment
import demidova.materialdesign.view.coordinator.CoordinatorFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    val binding: BottomNavigationLayoutBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.navigation_one -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ConstraintFragment.newInstance()).addToBackStack("").commit()
                }
                R.id.navigation_two -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.container, CoordinatorFragment.newInstance()).addToBackStack("").commit()
                }

                R.id.navigation_three -> {
                    startActivity(Intent(requireActivity(),AnimationsActivity::class.java))
                }
            }
            dismiss()

            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}