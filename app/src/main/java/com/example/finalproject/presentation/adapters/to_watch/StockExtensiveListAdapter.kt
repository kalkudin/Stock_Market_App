package com.example.finalproject.presentation.adapters.to_watch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemStockExtensiveListLayoutBinding
import com.example.finalproject.presentation.model.home.Stock
import com.example.finalproject.presentation.util.setTextColor
import com.example.finalproject.presentation.util.setBackground

class StockExtensiveListAdapter(private val onStockClicked: (Stock) -> Unit) :
    ListAdapter<Stock, StockExtensiveListAdapter.StockViewHolder>(StockDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val binding = ItemStockExtensiveListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = getItem(position)
        holder.bind(stock)
    }

    inner class StockViewHolder(
        private val binding: ItemStockExtensiveListLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(stock: Stock) {
            with(binding) {
                tvSymbol.text = stock.symbol
                tvName.text = stock.name
                tvPrice.text = stock.price
                tvChange.text = stock.change
                tvChangePercent.text = stock.changesPercentage

                val greenColor = ContextCompat.getColor(itemView.context, R.color.deaf_green)
                val redColor = ContextCompat.getColor(itemView.context, R.color.red)

                setTextColor(
                    if (stock.changeValue >= 0) greenColor
                    else redColor, tvPrice
                )

                setTextColor(
                    if (stock.changeValue >= 0) greenColor
                    else redColor, tvChangePercent
                )

                icGrowthIndicator.setImageResource(
                    if (stock.changeValue >= 0) R.drawable.ic_arrow_up
                    else R.drawable.ic_arrow_down
                )

                root.setBackgroundResource(
                    if (stock.changeValue >= 0) R.drawable.style_stocks_to_watch_best_gradient
                    else R.drawable.style_stocks_to_watch_worst_gradient
                )

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