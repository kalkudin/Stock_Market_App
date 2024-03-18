package com.example.finalproject.presentation.adapters.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemProfileFavoriteStockLayoutBinding
import com.example.finalproject.presentation.extension.loadImage
import com.example.finalproject.presentation.model.company_details.CompanyDetailsModel
import com.example.finalproject.presentation.model.home.Stock
import com.example.finalproject.presentation.util.setBackground
import com.example.finalproject.presentation.util.setTextColor
import okhttp3.internal.immutableListOf

class ProfileFavoriteStocksRecyclerAdapter() :
    ListAdapter<CompanyDetailsModel, ProfileFavoriteStocksRecyclerAdapter.ProfileFavoriteStocksViewHolder>(
        DiffCallback
    ) {
    companion object DiffCallback : DiffUtil.ItemCallback<CompanyDetailsModel>() {
        override fun areItemsTheSame(
            oldItem: CompanyDetailsModel,
            newItem: CompanyDetailsModel
        ): Boolean {
            return oldItem.companyName == newItem.companyName
        }

        override fun areContentsTheSame(
            oldItem: CompanyDetailsModel,
            newItem: CompanyDetailsModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class ProfileFavoriteStocksViewHolder(private val binding: ItemProfileFavoriteStockLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(stock: CompanyDetailsModel) {
            with(binding) {

                val greenColor = ContextCompat.getColor(itemView.context, R.color.deaf_green)
                val redColor = ContextCompat.getColor(itemView.context, R.color.red)

                ivImage.loadImage(stock.image)

                tvSymbol.text = stock.symbol
                tvName.text = stock.companyName
                tvPrice.text = "${stock.price.toString()} $"

                val changeValue = stock.changes ?: 0f
                val changeText = "(${changeValue})"
                tvChange.text = changeText

                if (changeValue >= 0) {
                    setTextColor(greenColor, tvChange)
                    icGrowthIndicator.setImageResource(R.drawable.ic_arrow_up)
                    setBackground(Stock.PerformingType.BEST_PERFORMING,stockContainer)
                } else {
                    setTextColor(redColor, tvChange)
                    icGrowthIndicator.setImageResource(R.drawable.ic_arrow_down)
                    setBackground(Stock.PerformingType.WORST_PERFORMING, stockContainer)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileFavoriteStocksViewHolder {
        val binding = ItemProfileFavoriteStockLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProfileFavoriteStocksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileFavoriteStocksViewHolder, position: Int) {
        val currentFavorite = getItem(position)
        holder.bind(currentFavorite)
    }

}