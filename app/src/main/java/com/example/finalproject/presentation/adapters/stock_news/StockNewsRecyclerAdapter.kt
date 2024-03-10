package com.example.finalproject.presentation.adapters.stock_news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemStockNewsVerticalBinding
import com.example.finalproject.presentation.extension.loadImage
import com.example.finalproject.presentation.model.stock_news.NewsModel

class StockNewsRecyclerAdapter(
    private val onItemClick: (NewsModel) -> Unit
): PagingDataAdapter<NewsModel, StockNewsRecyclerAdapter.StockNewsViewHolder>(StockNewsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockNewsViewHolder {
        val binding = ItemStockNewsVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockNewsViewHolder, position: Int) {
            holder.bind()
    }

    inner class StockNewsViewHolder(private val binding: ItemStockNewsVerticalBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var news : NewsModel
        fun bind() {
            news = getItem(absoluteAdapterPosition)!!
            binding.apply {
                ivArticle.loadImage(news.image)
                tvTitle.text = news.title
                tvContent.text = news.content
                tvDate.text = news.date
                tvAuthor.text = news.author
                tvSource.text = news.site
            }
            itemView.setOnClickListener {
                onItemClick(news)
            }
        }
    }

    private class StockNewsDiffUtil: DiffUtil.ItemCallback<NewsModel>() {
        override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem == newItem
        }
    }
}