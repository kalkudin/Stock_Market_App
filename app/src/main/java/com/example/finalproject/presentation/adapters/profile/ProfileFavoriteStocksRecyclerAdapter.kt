package com.example.finalproject.presentation.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemProfileFavoriteStockLayoutBinding
import com.example.finalproject.presentation.model.company_details.CompanyDetailsModel

class ProfileFavoriteStocksRecyclerAdapter() : ListAdapter<CompanyDetailsModel, ProfileFavoriteStocksRecyclerAdapter.ProfileFavoriteStocksViewHolder>(
    DiffCallback
){
    companion object DiffCallback : DiffUtil.ItemCallback<CompanyDetailsModel>(){
        override fun areItemsTheSame(oldItem: CompanyDetailsModel, newItem: CompanyDetailsModel): Boolean {
            return oldItem.companyName == newItem.companyName
        }

        override fun areContentsTheSame(oldItem: CompanyDetailsModel, newItem: CompanyDetailsModel): Boolean {
            return oldItem == newItem
        }
    }

    inner class ProfileFavoriteStocksViewHolder(private val binding : ItemProfileFavoriteStockLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stock : CompanyDetailsModel) {
            with(binding) {
                tvSymbol.text = stock.symbol
                tvName.text = stock.companyName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileFavoriteStocksViewHolder {
        val binding = ItemProfileFavoriteStockLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileFavoriteStocksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileFavoriteStocksViewHolder, position: Int) {
        val currentFavorite = getItem(position)
        holder.bind(currentFavorite)
    }

}