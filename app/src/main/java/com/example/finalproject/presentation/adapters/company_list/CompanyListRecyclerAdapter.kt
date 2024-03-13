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
import com.example.finalproject.presentation.model.company_list.CompanyListModel
import com.example.finalproject.presentation.util.setTextColor

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

                val greenColor = ContextCompat.getColor(itemView.context, R.color.deaf_green)
                val redColor = ContextCompat.getColor(itemView.context, R.color.red)
                val defColor = ContextCompat.getColor(itemView.context, R.color.sky_blue)

                when {
                    item.percentageChange > 0 -> {
                        ivArrow.setImageResource(R.drawable.ic_arrow_up)
                        ivArrow.isVisible = true
                        setTextColor(greenColor, tvCompanyPriceChangePercentage)
                    }
                    item.percentageChange < 0 -> {
                        ivArrow.setImageResource(R.drawable.ic_arrow_down)
                        ivArrow.isVisible = true
                        setTextColor(redColor, tvCompanyPriceChangePercentage)
                    }
                    else -> {
                        ivArrow.visibility = View.INVISIBLE
                        setTextColor(defColor, tvCompanyPriceChangePercentage)
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

    private class CompanyListDiffUtil : DiffUtil.ItemCallback<CompanyListModel>() {
        override fun areItemsTheSame(
            oldItem: CompanyListModel,
            newItem: CompanyListModel
        ): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(
            oldItem: CompanyListModel,
            newItem: CompanyListModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}