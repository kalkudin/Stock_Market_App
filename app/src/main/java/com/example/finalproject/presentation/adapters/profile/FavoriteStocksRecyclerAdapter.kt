package com.example.finalproject.presentation.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemFavoriteStockLayoutBinding
import com.example.finalproject.presentation.model.company_details.CompanyDetails

class FavoriteStocksRecyclerAdapter(
    private val onViewMoreClick: (CompanyDetails) -> Unit,
    private val onStockLongClick: (CompanyDetails) -> Unit
): ListAdapter<CompanyDetails, FavoriteStocksRecyclerAdapter.FavoriteStockViewHolder>(
    DiffCallback
) {

    companion object DiffCallback : DiffUtil.ItemCallback<CompanyDetails>() {
        override fun areItemsTheSame(oldItem: CompanyDetails, newItem: CompanyDetails): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: CompanyDetails, newItem: CompanyDetails): Boolean {
            return oldItem == newItem
        }
    }

    inner class FavoriteStockViewHolder(private val binding: ItemFavoriteStockLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(companyDetails: CompanyDetails) {
            with(binding) {
                tvSymbol.text = companyDetails.symbol
                tvName.text = companyDetails.companyName

                root.setOnLongClickListener {
                    onStockLongClick(companyDetails)
                    true
                }

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