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
    private val onBestStocksViewMoreClick: () -> Unit,
    private val onWorstStocksViewMoreClick: () -> Unit,
    private val onActiveStocksViewMoreClick: () -> Unit
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
        holder.bind(bestStocks, worstStocks, activeStocks, onStockClick)
    }

    override fun getItemCount(): Int = 1

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(best: List<Stock>, worst: List<Stock>, active: List<Stock>) {
        this.bestStocks = best
        this.worstStocks = worst
        this.activeStocks = active
        notifyDataSetChanged()
    }

    inner class StockHostViewHolder(private val binding: ItemStockHomeHostRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            bestStocks: List<Stock>,
            worstStocks: List<Stock>,
            activeStocks: List<Stock>,
            onStockClick: (Stock) -> Unit
        ) {
            binding.rvBestStocks.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = StockListAdapter(onStockClick).apply { submitList(bestStocks) }
            }

            binding.rvWorstStocks.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter =
                    StockListAdapter(onStockClick).apply { submitList(worstStocks) }
            }

            binding.rvActiveStocks.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter =
                    StockListAdapter(onStockClick).apply { submitList(activeStocks) }
            }

            binding.btnViewMoreBestStocks.setOnClickListener { onBestStocksViewMoreClick.invoke() }
            binding.btnViewMoreWorstStocks.setOnClickListener { onWorstStocksViewMoreClick.invoke() }
            binding.btnViewMoreActiveStocks.setOnClickListener { onActiveStocksViewMoreClick.invoke() }
        }
    }
}