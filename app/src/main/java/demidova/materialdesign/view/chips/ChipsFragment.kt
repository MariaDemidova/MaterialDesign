package demidova.materialdesign.view.chips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import demidova.materialdesign.databinding.FragmentChipsBinding


class ChipsFragment : Fragment() {

    private var _binding: FragmentChipsBinding? = null
    val binding: FragmentChipsBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            group.findViewById<Chip>(checkedId)?.let {
                Toast.makeText(context, "choose ${it.text}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.chipForDelete.setOnCloseIconClickListener {
            binding.chipForDelete.isChecked = false
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ChipsFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}