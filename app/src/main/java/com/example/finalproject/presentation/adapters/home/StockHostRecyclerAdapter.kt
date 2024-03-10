package com.example.finalproject.presentation.adapters.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemStockHomeHostRecyclerBinding
import com.example.finalproject.presentation.model.home.Stock

class StockHostRecyclerAdapter(
    private val onStockClick: (Stock) -> Unit,
    private val onViewMoreClick: (Stock.PerformingType) -> Unit,
    private val onAddFundsClick: () -> Unit,
    private val onStockNewsClick: () -> Unit
) : RecyclerView.Adapter<StockHostRecyclerAdapter.StockHostViewHolder>() {

    private var bestStocks: List<Stock> = emptyList()
    private var worstStocks: List<Stock> = emptyList()
    private var activeStocks: List<Stock> = emptyList()
    private var userFunds: String = "0.0"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockHostViewHolder {
        val binding = ItemStockHomeHostRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockHostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockHostViewHolder, position: Int) {
        holder.bind()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(best: List<Stock>, worst: List<Stock>, active: List<Stock>, funds: String = "") {
        bestStocks = best
        worstStocks = worst
        activeStocks = active
        userFunds = funds
        notifyDataSetChanged()
    }

    inner class StockHostViewHolder(private val binding: ItemStockHomeHostRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            with(binding) {
                bindStocks(rvBestStocks, bestStocks)
                bindStocks(rvWorstStocks, worstStocks)
                bindStocks(rvActiveStocks, activeStocks)

                btnViewMoreBestStocks.setOnClickListener { onViewMoreClick(Stock.PerformingType.BEST_PERFORMING) }
                btnViewMoreWorstStocks.setOnClickListener { onViewMoreClick(Stock.PerformingType.WORST_PERFORMING) }
                btnViewMoreActiveStocks.setOnClickListener { onViewMoreClick(Stock.PerformingType.ACTIVE_PERFORMING) }

                btnAddMoreFunds.setOnClickListener { onAddFundsClick() }

                binding.tvAmount.text = userFunds
            }
        }

        private fun bindStocks(rv : RecyclerView, stockList : List<Stock>) {
            rv.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = StockListRecyclerAdapter(onStockClick).apply { submitList(stockList) }
            }
        }
    }
    override fun getItemCount(): Int = 1
}