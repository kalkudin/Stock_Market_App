package com.example.finalproject.presentation.adapters.to_watch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemStockExtensiveListLayoutBinding
import com.example.finalproject.presentation.model.home.Stock

class StockExtensiveListAdapter(private val onStockClicked: (Stock) -> Unit) :
    ListAdapter<Stock, StockExtensiveListAdapter.StockViewHolder>(StockDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val binding = ItemStockExtensiveListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockViewHolder(binding, onStockClicked)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = getItem(position)
        holder.bind(stock)
    }

    inner class StockViewHolder(
        private val binding: ItemStockExtensiveListLayoutBinding,
        private val onStockClicked: (Stock) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(stock: Stock) {
            with(binding) {
                tvSymbol.text = stock.symbol
                tvName.text = stock.name
                tvPrice.text = stock.price
                tvChange.text = stock.change
                tvChangePercent.text = stock.changesPercentage
                icGrowthIndicator.setImageResource(if (stock.changeValue >= 0) R.drawable.ic_up else R.drawable.ic_down)

                root.setOnClickListener { onStockClicked(stock) }
            }
        }
    }

    companion object {
        private val StockDiffCallback = object : DiffUtil.ItemCallback<Stock>() {
            override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean = oldItem.symbol == newItem.symbol
            override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean = oldItem == newItem
        }
    }
}