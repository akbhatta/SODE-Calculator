package com.sode.calculator.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sode.calculator.data.SODEContext
import com.sode.calculator.databinding.FragmentReportBinding

class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentReportBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textReport = binding.textReport
        val adapter = TableViewAdapter()
        textReport.setAdapter(adapter)
        val columns = (0..(SODEContext.FUNCTIONS.size - 1)).toList()
        val rows = generateSequence(SODEContext.initX) { previous ->
            val next = previous + SODEContext.increment
            if (next > SODEContext.finalX) null else next
        }.toList()
        val cellItems = (0..(rows.size-1)).map { index -> SODEContext.graphPoints.map { it -> it.points[index] }.toList() }.toList()
        adapter.setAllItems(columns, rows, cellItems)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}