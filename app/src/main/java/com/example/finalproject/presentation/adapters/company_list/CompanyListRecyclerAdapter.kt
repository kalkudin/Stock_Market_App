package com.example.finalproject.presentation.adapters.company_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemCompanyListBinding
import com.example.finalproject.presentation.model.company_list.CompanyListModel

class CompanyListRecyclerAdapter(
    val onCompanyClick: (CompanyListModel) -> Unit
) : ListAdapter<CompanyListModel, CompanyListRecyclerAdapter.CompanyListViewHolder>(
    CompanyListDiffUtil()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompanyListViewHolder {
        val binding =
            ItemCompanyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CompanyListViewHolder,
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
                tvCompanyPrice.text = item.price.toString()
                tvExchange.text = item.exchangeShortName
                tvType.text = item.type
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