package com.example.finalproject.presentation.adapters.profile


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemTransactionLayoutBinding
import com.example.finalproject.presentation.mapper.home.formatUserFunds
import com.example.finalproject.presentation.model.profile.Transaction
import com.example.finalproject.presentation.util.setTextColor

class TransactionRecyclerAdapter : ListAdapter<Transaction, TransactionRecyclerAdapter.TransactionViewHolder>(
    DiffUtilCallback
) {
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

                val greenColor = ContextCompat.getColor(itemView.context, R.color.deaf_green)
                val blueColor = ContextCompat.getColor(itemView.context, R.color.ocean_blue)
                val redColor = ContextCompat.getColor(itemView.context, R.color.red)
                val whiteColor = ContextCompat.getColor(itemView.context, R.color.white)
                val lightOrange = ContextCompat.getColor(itemView.context, R.color.light_orange)



                when(transaction.type) {
                    Transaction.TransactionType.OUTGOING -> {
                        setTextColor(greenColor, tvType)
                    }
                    Transaction.TransactionType.INTERNAL -> {
                        setTextColor(redColor, tvType)
                    }
                    Transaction.TransactionType.BUY -> {
                        setTextColor(whiteColor, tvType)
                    }
                    Transaction.TransactionType.SELL -> {
                        setTextColor(lightOrange, tvType)
                    }
                    Transaction.TransactionType.UNSPECIFIED -> {
                        setTextColor(blueColor, tvType)
                    }
                }
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