package com.example.finalproject.presentation.adapters.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemStockCardLayoutBinding
import com.example.finalproject.presentation.model.home.Stock

class StockListRecyclerAdapter(private val onItemClick: (Stock) -> Unit): ListAdapter<Stock, StockListRecyclerAdapter.StockViewHolder>(
    DiffCallback
) {

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
                icGrowthIndicator.setImageResource(
                    if (stock.changeValue >= 0) R.drawable.ic_up else R.drawable.ic_down
                )

                root.setOnClickListener { onItemClick(stock) }
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