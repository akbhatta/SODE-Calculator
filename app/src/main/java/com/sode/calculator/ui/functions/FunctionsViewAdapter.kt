package com.sode.calculator.ui.functions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.sode.calculator.data.SODEContext
import com.sode.calculator.data.SODEFunction
import com.sode.calculator.databinding.SodeFunctionItemBinding

/**
 * [androidx.recyclerview.widget.RecyclerView.Adapter] that can display a [com.sode.calculator.data.SODEFunction].
 */
class FunctionsViewAdapter() : RecyclerView.Adapter<FunctionsViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            SodeFunctionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = SODEContext.FUNCTIONS[position]
        holder.functionEvaluationView.text = getInfoText(position, item)
        holder.editText.setText(item.functionStr, TextView.BufferType.EDITABLE)
        holder.editText.doAfterTextChanged {
            item.functionStr = it.toString()
            holder.setText(getInfoText(position, item))
        }
    }

    private fun getInfoText(position: Int, item: SODEFunction): String {
        return "y$position' = ${item.evaluate()}"
    }

    override fun getItemCount(): Int = SODEContext.FUNCTIONS.size

    inner class ViewHolder(binding: SodeFunctionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val functionEvaluationView = binding.functionExpression
        val editText = binding.functionText
        override fun toString(): String {
            return super.toString() + " '" + editText.text + "'"
        }

        fun setText(text: String) {
            functionEvaluationView.text = text
        }
    }
}