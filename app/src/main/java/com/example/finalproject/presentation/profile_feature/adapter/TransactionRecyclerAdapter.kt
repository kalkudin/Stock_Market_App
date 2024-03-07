package com.example.finalproject.presentation.profile_feature.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemTransactionLayoutBinding
import com.example.finalproject.presentation.profile_feature.model.Transaction
import com.example.finalproject.presentation.stock_feature.home.mapper.formatUserFunds

class TransactionRecyclerAdapter : ListAdapter<Transaction, TransactionRecyclerAdapter.TransactionViewHolder>(DiffUtilCallback) {
    companion object DiffUtilCallback: DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }
    inner class TransactionViewHolder(private val binding : ItemTransactionLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            with(binding) {
                tvAmount.text = formatUserFunds(transaction.amount)
                tvDate.text = transaction.date
                tvDescription.text = transaction.description
                tvType.text = transaction.type.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction)
    }
}