package com.example.finalproject.presentation.adapters.owned_stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemOwnedStockLayoutBinding
import com.example.finalproject.presentation.model.owned_stocks.OwnedStock

class OwnedStocksRecyclerAdapter(
    private val onCompanyClick: (OwnedStock) -> Unit
): ListAdapter<OwnedStock, OwnedStocksRecyclerAdapter.OwnedStocksViewHolder>(
    OwnedStocksRecyclerAdapter.OwnedStocksDiffUtil()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OwnedStocksRecyclerAdapter.OwnedStocksViewHolder {
        val binding = ItemOwnedStockLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OwnedStocksViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OwnedStocksRecyclerAdapter.OwnedStocksViewHolder,
        position: Int
    ) {
        holder.bind()
    }

    inner class OwnedStocksViewHolder(
        private val binding: ItemOwnedStockLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private lateinit var item: OwnedStock
        fun bind() {
            item = currentList[adapterPosition]
            binding.apply {
                tvCompanySymbol.text = item.symbol
                tvAmount.text = "$${item.amount}"
            }
            itemView.setOnClickListener {
                onCompanyClick.invoke(item)
            }
        }
    }
    private class OwnedStocksDiffUtil : DiffUtil.ItemCallback<OwnedStock>() {
        override fun areItemsTheSame(
            oldItem: OwnedStock,
            newItem: OwnedStock
        ): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(
            oldItem: OwnedStock,
            newItem: OwnedStock
        ): Boolean {
            return oldItem == newItem
        }
    }
}