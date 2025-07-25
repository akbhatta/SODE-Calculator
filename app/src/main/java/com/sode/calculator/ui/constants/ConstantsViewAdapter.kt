package com.sode.calculator.ui.constants

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.sode.calculator.data.SODEContext
import com.sode.calculator.databinding.SodeConstantItemBinding

/**
 * [androidx.recyclerview.widget.RecyclerView.Adapter] that can take initial values.
 */
class ConstantsViewAdapter() : RecyclerView.Adapter<ConstantsViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            SodeConstantItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < SODEContext.COORDINATES.size) {
            val item = SODEContext.COORDINATES[position]
            holder.nameView.text = item.constant
            holder.editText.setText(item.valueStr, TextView.BufferType.EDITABLE)
            holder.editText.doAfterTextChanged {
                item.valueStr = it.toString()
            }
        } else if (position < (SODEContext.COORDINATES.size + SODEContext.FUNCTIONS.size)) {
            val index = position - SODEContext.COORDINATES.size
            val item = SODEContext.FUNCTIONS[index]
            holder.nameView.text = "y${index} = "
            holder.editText.setText(item.initValStr, TextView.BufferType.EDITABLE)
            holder.editText.doAfterTextChanged {
                item.initValStr = it.toString()
            }
        } else {
            val index = position - SODEContext.COORDINATES.size - SODEContext.FUNCTIONS.size
            val item = SODEContext.CONSTANTS[index]
            holder.nameView.text = "${item.constant} = "
            holder.editText.setText(item.valueStr, TextView.BufferType.EDITABLE)
            holder.editText.doAfterTextChanged {
                item.valueStr = it.toString()
            }
        }
    }

    override fun getItemCount(): Int =
        SODEContext.COORDINATES.size + SODEContext.FUNCTIONS.size + SODEContext.CONSTANTS.size

    inner class ViewHolder(binding: SodeConstantItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameView = binding.constantName
        val editText = binding.constantValue

        override fun toString(): String {
            return super.toString() + " '" + editText.text + "'"
        }
    }
}