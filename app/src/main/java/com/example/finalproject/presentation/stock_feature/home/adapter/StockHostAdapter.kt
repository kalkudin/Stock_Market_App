package com.example.finalproject.presentation.stock_feature.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemStockHomeHostRecyclerBinding
import com.example.finalproject.presentation.stock_feature.home.model.Stock

class StockHostAdapter(
    private val onStockClick: (Stock) -> Unit,
    private val onViewMoreClick: (Stock.PerformingType) -> Unit
) : RecyclerView.Adapter<StockHostAdapter.StockHostViewHolder>() {

    private var bestStocks: List<Stock> = emptyList()
    private var worstStocks: List<Stock> = emptyList()
    private var activeStocks: List<Stock> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockHostViewHolder {
        val binding = ItemStockHomeHostRecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StockHostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockHostViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(best: List<Stock>, worst: List<Stock>, active: List<Stock>) {
        bestStocks = best
        worstStocks = worst
        activeStocks = active
        notifyDataSetChanged()
    }

    inner class StockHostViewHolder(private val binding: ItemStockHomeHostRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            bindStocks(binding.rvBestStocks, bestStocks)
            bindStocks(binding.rvWorstStocks, worstStocks)
            bindStocks(binding.rvActiveStocks, activeStocks)

            binding.btnViewMoreBestStocks.setOnClickListener { onViewMoreClick(Stock.PerformingType.BEST_PERFORMING) }
            binding.btnViewMoreWorstStocks.setOnClickListener { onViewMoreClick(Stock.PerformingType.WORST_PERFORMING) }
            binding.btnViewMoreActiveStocks.setOnClickListener { onViewMoreClick(Stock.PerformingType.ACTIVE_PERFORMING) }
        }

        private fun bindStocks(rv : RecyclerView, stockList : List<Stock>) {
            rv.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = StockListAdapter(onStockClick).apply { submitList(stockList) }
            }
        }
    }
}