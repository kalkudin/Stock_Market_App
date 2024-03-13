package com.example.finalproject.presentation.adapters.watchlisted_stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemWatchlistLayoutBinding
import com.example.finalproject.presentation.model.company_details.CompanyDetailsModel
import com.example.finalproject.presentation.util.setTextColor

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

                val greenColor = ContextCompat.getColor(itemView.context, R.color.deaf_green)
                val redColor = ContextCompat.getColor(itemView.context, R.color.red)
                val defColor = ContextCompat.getColor(itemView.context, R.color.sky_blue)

                when {
                    item.changes!! > 0 -> {
                        ivArrow.setImageResource(R.drawable.ic_arrow_up)
                        ivArrow.isVisible = true
                        setTextColor(greenColor, tvCompanyPriceChange)
                    }
                    item.changes!! < 0 -> {
                        ivArrow.setImageResource(R.drawable.ic_arrow_down)
                        ivArrow.isVisible = true
                        setTextColor(redColor, tvCompanyPriceChange)
                    }
                    else -> {
                        ivArrow.isVisible = false
                        setTextColor(defColor, tvCompanyPriceChange)
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