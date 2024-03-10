package com.example.finalproject.presentation.adapters.watchlisted_stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemWatchlistLayoutBinding
import com.example.finalproject.presentation.model.company_details.CompanyDetailsModel

class WatchlistedStocksRecyclerAdapter(
    val onCompanyClick: (CompanyDetailsModel) -> Unit,
    val onDeleteButtonClick:(CompanyDetailsModel) -> Unit
) : ListAdapter<CompanyDetailsModel, WatchlistedStocksRecyclerAdapter.WatchlistedStocksViewHolder>(
    WatchlistedStocksDiffUtil()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WatchlistedStocksRecyclerAdapter.WatchlistedStocksViewHolder {
        val binding =
            ItemWatchlistLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WatchlistedStocksViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: WatchlistedStocksRecyclerAdapter.WatchlistedStocksViewHolder,
        position: Int
    ) {
        holder.bind()
    }

    inner class WatchlistedStocksViewHolder(
        private val binding: ItemWatchlistLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private lateinit var item: CompanyDetailsModel
        fun bind() {
            item = currentList[adapterPosition]
            binding.apply {
                when {
                    item.changes!! > 0 -> {
                        ivArrow.setImageResource(R.drawable.ic_arrow_up_list)
                        ivArrow.isVisible = true
                    }
                    item.changes!! < 0 -> {
                        ivArrow.setImageResource(R.drawable.ic_arrow_down_list)
                        ivArrow.isVisible = true
                    }
                    else -> {
                        ivArrow.isVisible = false
                    }
                }
                tvCompanyName.text = item.companyName
                tvCompanySymbol.text = item.symbol
                tvCompanyPrice.text = "${item.price}$"
                tvCompanyPriceChange.text = "${item.changes}$"
                itemView.setOnClickListener {
                    onCompanyClick.invoke(item)
                }
                btnDelete.setOnClickListener {
                    onDeleteButtonClick.invoke(item)
                }
            }
        }
    }

    private class WatchlistedStocksDiffUtil : DiffUtil.ItemCallback<CompanyDetailsModel>() {
        override fun areItemsTheSame(
            oldItem: CompanyDetailsModel,
            newItem: CompanyDetailsModel
        ): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(
            oldItem: CompanyDetailsModel,
            newItem: CompanyDetailsModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}