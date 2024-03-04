package com.example.finalproject.presentation.profile_feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemFavoriteStockLayoutBinding
import com.example.finalproject.presentation.stock_feature.company_details.model.CompanyDetailsModel
import com.example.finalproject.presentation.util.setCustomGradientBackground

class FavoriteStocksRecyclerAdapter(
    private val onViewMoreClick: (CompanyDetailsModel) -> Unit,
    private val onStockLongClick: (CompanyDetailsModel) -> Unit
): ListAdapter<CompanyDetailsModel, FavoriteStocksRecyclerAdapter.FavoriteStockViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<CompanyDetailsModel>() {
        override fun areItemsTheSame(oldItem: CompanyDetailsModel, newItem: CompanyDetailsModel): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: CompanyDetailsModel, newItem: CompanyDetailsModel): Boolean {
            return oldItem == newItem
        }
    }

    inner class FavoriteStockViewHolder(private val binding: ItemFavoriteStockLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(companyDetails: CompanyDetailsModel) {
            with(binding) {
                tvSymbol.text = companyDetails.symbol
                tvName.text = companyDetails.companyName

                root.setOnLongClickListener {
                    onStockLongClick(companyDetails)
                    true
                }

                root.setCustomGradientBackground()

                btnViewMore.setOnClickListener {
                    onViewMoreClick(companyDetails)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteStockViewHolder {
        val binding = ItemFavoriteStockLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteStockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteStockViewHolder, position: Int) {
        val currentStock = getItem(position)
        holder.bind(currentStock)
    }
}