package com.sode.calculator.ui.functions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sode.calculator.data.SODEContext
import com.sode.calculator.data.SODEFunction
import com.sode.calculator.databinding.FragmentFunctionsBinding

/**
 * A fragment representing a list of SODE Functions.
 */
class FunctionsFragment : Fragment() {

    private var _binding: FragmentFunctionsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var columnCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFunctionsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // Set the adapter
        with(binding.functionList) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = FunctionsViewAdapter()
        }
        binding.newFunctionFab.setOnClickListener { view ->
            SODEContext.FUNCTIONS.add(SODEFunction())
            binding.functionList.adapter?.notifyItemInserted(SODEContext.FUNCTIONS.size - 1)
        }
        binding.nextFab.setOnClickListener { view ->
            SODEContext.updateFunctionDependencies()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}