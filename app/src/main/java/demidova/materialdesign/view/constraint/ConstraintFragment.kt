package demidova.materialdesign.view.constraint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import demidova.materialdesign.databinding.FragmentConstraintBinding


class ConstraintFragment : Fragment() {


    private var _binding: FragmentConstraintBinding? = null
    val binding: FragmentConstraintBinding
        get() {
            return _binding!!
        }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConstraintBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.group1.visibility = View.GONE
        binding.group2.visibility = View.INVISIBLE

        binding.materialButton3.setOnClickListener {
            binding.group1.visibility = View.VISIBLE
            binding.group2.visibility = View.VISIBLE
        }
        binding.materialButton33.setOnClickListener {
            binding.group1.visibility = View.GONE
            binding.group2.visibility = View.INVISIBLE
        }*/
        //аналогично
        /*binding.materialButton1.visibility  =View.GONE
        binding.materialButton2.visibility  =View.GONE

        binding.materialButton11.visibility  =View.VISIBLE
        binding.materialButton22.visibility  =View.VISIBLE*/
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ConstraintFragment()
    }
}