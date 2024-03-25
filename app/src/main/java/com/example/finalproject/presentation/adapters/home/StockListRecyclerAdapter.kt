package com.example.finalproject.presentation.adapters.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemStockCardLayoutBinding
import com.example.finalproject.presentation.model.home.Stock
import com.example.finalproject.presentation.util.setBackground
import com.example.finalproject.presentation.util.setTextColor

class StockListRecyclerAdapter(private val onItemClick: (Stock) -> Unit): ListAdapter<Stock, StockListRecyclerAdapter.StockViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Stock>() {
        override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
            return oldItem.symbol == newItem.symbol
        }
        override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
            return oldItem == newItem
        }
    }

    inner class StockViewHolder(private var binding: ItemStockCardLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(stock: Stock) {
            with(binding) {

                tvSymbol.text = stock.symbol
                tvName.text = stock.name
                tvPrice.text = stock.price
                tvChange.text = stock.change
                tvChangePercent.text = stock.changesPercentage

                if(stock.changeValue >= 0 ) {
                    setTextColor(ContextCompat.getColor(itemView.context, R.color.deaf_green), tvPrice)
                    setTextColor(ContextCompat.getColor(itemView.context, R.color.deaf_green), tvChangePercent)
                    icGrowthIndicator.setImageResource(R.drawable.ic_arrow_up)
                } else {
                    setTextColor(ContextCompat.getColor(itemView.context, R.color.red), tvPrice)
                    setTextColor(ContextCompat.getColor(itemView.context, R.color.red), tvChangePercent)
                    icGrowthIndicator.setImageResource(R.drawable.ic_arrow_down)
                }

                root.apply {
                    setOnClickListener { onItemClick(stock) }
                    setBackground(stock.performingType, this)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(ItemStockCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = getItem(position)
        holder.bind(stock)
    }
}