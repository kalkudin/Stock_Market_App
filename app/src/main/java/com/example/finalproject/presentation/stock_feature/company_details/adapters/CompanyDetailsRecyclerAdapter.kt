package com.example.finalproject.presentation.stock_feature.company_details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemCompanyDetailsBinding
import com.example.finalproject.databinding.ItemCompanyListBinding
import com.example.finalproject.presentation.stock_feature.company_details.model.CompanyDetailsModel
import com.example.finalproject.presentation.stock_feature.company_list.model.CompanyListModel

class CompanyDetailsRecyclerAdapter :
    ListAdapter<CompanyDetailsModel, CompanyDetailsRecyclerAdapter.CompanyDetailsViewHolder>(
        CompanyDetailsDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompanyDetailsRecyclerAdapter.CompanyDetailsViewHolder {
        val binding =
            ItemCompanyDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CompanyDetailsRecyclerAdapter.CompanyDetailsViewHolder,
        position: Int
    ) {
        holder.bind()
    }

    inner class CompanyDetailsViewHolder(
        private val binding: ItemCompanyDetailsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var item: CompanyDetailsModel
        fun bind() {
            item = currentList[adapterPosition]
            binding.apply {
                tvSymbol.text = item.companyName
                tvName.text = item.symbol
                tvDesc.text = item.description
            }
        }
    }
    private class CompanyDetailsDiffUtil : DiffUtil.ItemCallback<CompanyDetailsModel>() {
        override fun areItemsTheSame(
            oldItem: CompanyDetailsModel,
            newItem: CompanyDetailsModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CompanyDetailsModel,
            newItem: CompanyDetailsModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}