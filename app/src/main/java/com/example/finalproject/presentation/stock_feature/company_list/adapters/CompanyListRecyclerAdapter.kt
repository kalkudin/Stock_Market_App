package com.example.finalproject.presentation.stock_feature.company_list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemCompanyListBinding
import com.example.finalproject.presentation.stock_feature.company_list.model.CompanyListModel

class CompanyListRecyclerAdapter(
    val onCompanyClick: (CompanyListModel) -> Unit
) : ListAdapter<CompanyListModel, CompanyListRecyclerAdapter.CompanyListViewHolder>(
    CompanyListDiffUtil()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompanyListRecyclerAdapter.CompanyListViewHolder {
        val binding =
            ItemCompanyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CompanyListRecyclerAdapter.CompanyListViewHolder,
        position: Int
    ) {
        holder.bind()
    }

    inner class CompanyListViewHolder(
        private val binding: ItemCompanyListBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private lateinit var item: CompanyListModel
        fun bind() {
            item = currentList[adapterPosition]
            binding.apply {
                tvCompanyName.text = item.name
                tvCompanySymbol.text = item.symbol
                tvCompanyPrice.text = "${item.price}$"
                tvCompanyPriceChange.text = "${item.priceChange}$"
                tvCompanyPriceChangePercentage.text = "${item.percentageChange}%"
                if (item.percentageChange > 0) {
                    ivArrow.setImageResource(R.drawable.ic_arrow_up_list)
                } else if (item.percentageChange < 0) {
                    ivArrow.setImageResource(R.drawable.ic_arrow_down_list)
                }else {
                    ivArrow.isVisible = false
                }
                itemView.setOnClickListener {
                    onCompanyClick.invoke(item)
                }
            }
        }
    }

    private class CompanyListDiffUtil : DiffUtil.ItemCallback<CompanyListModel>() {
        override fun areItemsTheSame(
            oldItem: CompanyListModel,
            newItem: CompanyListModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CompanyListModel,
            newItem: CompanyListModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}