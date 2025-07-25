package com.sode.calculator.ui.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sode.calculator.data.SODEContext
import com.sode.calculator.databinding.FragmentGraphBinding
import com.softmoore.graphlib.Graph
import com.softmoore.graphlib.GraphView


class GraphFragment : Fragment() {

    private var _binding: FragmentGraphBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val graphBuilder = Graph.Builder()
        for (graphPoint in SODEContext.graphPoints) {
            graphBuilder.addLineGraph(graphPoint.color, graphPoint.points)
        }
        graphBuilder
            .setWorldCoordinates(
                SODEContext.initX,
                SODEContext.finalX,
                SODEContext.initY,
                SODEContext.finalY
            )
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val graphView: GraphView = binding.graphView
        graphView.setGraph(graphBuilder.build())
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}