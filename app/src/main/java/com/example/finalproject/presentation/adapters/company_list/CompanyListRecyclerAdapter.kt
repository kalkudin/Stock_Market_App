package com.example.finalproject.presentation.adapters.company_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemCompanyListBinding
import com.example.finalproject.presentation.model.company_list.CompanyList
import com.example.finalproject.presentation.util.setTextColor

class CompanyListRecyclerAdapter(
    val onCompanyClick: (CompanyList) -> Unit
) : ListAdapter<CompanyList, CompanyListRecyclerAdapter.CompanyListViewHolder>(
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
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CompanyListViewHolder(
        private val binding: ItemCompanyListBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CompanyList) {
            binding.apply {

                val greenColor = ContextCompat.getColor(itemView.context, R.color.deaf_green)
                val redColor = ContextCompat.getColor(itemView.context, R.color.red)
                val defColor = ContextCompat.getColor(itemView.context, R.color.sky_blue)

                when {
                    item.percentageChange > 0 -> {
                        ivArrow.setImageResource(R.drawable.ic_arrow_up)
                        ivArrow.isVisible = true
                        setTextColor(greenColor, tvCompanyPriceChangePercentage)
                        setTextColor(greenColor, tvCompanyPriceChange)
                    }
                    item.percentageChange < 0 -> {
                        ivArrow.setImageResource(R.drawable.ic_arrow_down)
                        ivArrow.isVisible = true
                        setTextColor(redColor, tvCompanyPriceChangePercentage)
                        setTextColor(redColor, tvCompanyPriceChange)
                    }
                    else -> {
                        ivArrow.visibility = View.INVISIBLE
                        setTextColor(defColor, tvCompanyPriceChangePercentage)
                        setTextColor(defColor, tvCompanyPriceChange)
                    }
                }
                tvCompanyName.text = item.name
                tvCompanySymbol.text = item.symbol
                tvCompanyPrice.text = "${item.price}$"
                tvCompanyPriceChange.text = "${item.priceChange}$"
                tvCompanyPriceChangePercentage.text = "(${item.percentageChange}%)"
                itemView.setOnClickListener {
                    onCompanyClick.invoke(item)
                }
            }
        }
    }

    private class CompanyListDiffUtil : DiffUtil.ItemCallback<CompanyList>() {
        override fun areItemsTheSame(
            oldItem: CompanyList,
            newItem: CompanyList
        ): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(
            oldItem: CompanyList,
            newItem: CompanyList
        ): Boolean {
            return oldItem == newItem
        }
    }
}