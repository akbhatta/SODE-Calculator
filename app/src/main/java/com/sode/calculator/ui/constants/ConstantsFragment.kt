package com.sode.calculator.ui.constants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sode.calculator.data.SODEContext
import com.sode.calculator.databinding.FragmentConstantsBinding

/**
 * A fragment representing a list of SODE Functions initial values and constant values.
 */
class ConstantsFragment : Fragment() {

    private var _binding: FragmentConstantsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var columnCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConstantsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // Set the adapter
        with(binding.functionList) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = ConstantsViewAdapter()
        }
        binding.evaluateFab.setOnClickListener { view ->
            SODEContext.createGraphPoints()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
