package com.example.finalproject.presentation.stock_feature.stock_news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemStockNewsBinding
import com.example.finalproject.presentation.extension.loadImage
import com.example.finalproject.presentation.stock_feature.stock_news.model.StockNewsModel

class StockNewsPagingAdapter :
    PagingDataAdapter<StockNewsModel, StockNewsPagingAdapter.StockNewsViewHolder>(
        StockNewsPagingDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockNewsViewHolder {
        val binding =
            ItemStockNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockNewsViewHolder, position: Int) {
        holder.bind()
    }

    inner class StockNewsViewHolder(private val binding: ItemStockNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
            private lateinit var item: StockNewsModel
            fun bind(){
                item = getItem(bindingAdapterPosition)!!
                binding.apply {
                    ivArticle.loadImage(item.image)
                    tvTitle.text = item.title
                    tvContent.text = item.content
                }
            }
    }

    private class StockNewsPagingDiffUtil : DiffUtil.ItemCallback<StockNewsModel>() {
        override fun areItemsTheSame(oldItem: StockNewsModel, newItem: StockNewsModel): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: StockNewsModel, newItem: StockNewsModel): Boolean {
            return oldItem == newItem
        }

    }
}