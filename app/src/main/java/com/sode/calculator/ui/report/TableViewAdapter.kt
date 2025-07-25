package com.sode.calculator.ui.report

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.evrencoskun.tableview.adapter.AbstractTableAdapter
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.sode.calculator.R
import com.softmoore.graphlib.Point

/**
 * TableViewAdapter for showing calculated values in table form.
 */
class TableViewAdapter() : AbstractTableAdapter<Int, Double, Point>() {

    override fun onCreateCellViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.table_view_cell_layout, parent, false)
        return CellViewHolder(view as TextView)
    }

    override fun onBindCellViewHolder(
        holder: AbstractViewHolder, cellItemModel: Point?, columnPosition: Int, rowPosition: Int
    ) {
        val cellViewHolder = holder as CellViewHolder
        cellViewHolder.itemView.text = cellItemModel?.y.toString()
    }

    override fun onCreateColumnHeaderViewHolder(
        parent: ViewGroup, viewType: Int
    ): AbstractViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.table_column_header_layout, parent, false)
        return CellViewHolder(view as TextView)
    }

    override fun onBindColumnHeaderViewHolder(
        holder: AbstractViewHolder, columnHeader: Int?, columnPosition: Int
    ) {
        val cellViewHolder = holder as CellViewHolder
        cellViewHolder.itemView.text = "y${columnHeader}"
    }

    override fun onCreateRowHeaderViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.table_row_header_layout, parent, false)
        return CellViewHolder(view as TextView)
    }

    @SuppressLint("DefaultLocale")
    override fun onBindRowHeaderViewHolder(
        holder: AbstractViewHolder, rowHeader: Double?, rowPosition: Int
    ) {
        val cellViewHolder = holder as CellViewHolder
        cellViewHolder.itemView.text = String.format("%.3f", rowHeader)
    }

    override fun onCreateCornerView(parent: ViewGroup): View {
        val inflater = LayoutInflater.from(parent.context)
        return inflater.inflate(R.layout.table_view_corner_layout, parent, false)
    }

    override fun getColumnHeaderItemViewType(position: Int): Int = 0

    override fun getRowHeaderItemViewType(position: Int): Int = 0

    override fun getCellItemViewType(column: Int): Int = 0

    internal class CellViewHolder(val itemView: TextView) : AbstractViewHolder(itemView)
}