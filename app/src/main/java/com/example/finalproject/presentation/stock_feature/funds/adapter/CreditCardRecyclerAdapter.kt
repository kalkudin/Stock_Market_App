package com.example.finalproject.presentation.stock_feature.funds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemCreditCardLayoutBinding
import com.example.finalproject.presentation.stock_feature.funds.mapper.maskAndFormatCardNumber
import com.example.finalproject.presentation.stock_feature.funds.model.CreditCard

class CreditCardRecyclerAdapter (private val onItemClick: (CreditCard) -> Unit): ListAdapter<CreditCard, CreditCardRecyclerAdapter.CreditCardViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditCardViewHolder {
        val binding = ItemCreditCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CreditCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CreditCardViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CreditCardViewHolder(private val binding: ItemCreditCardLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(creditCard: CreditCard) {
            with(binding) {
                tvCardNumber.text = maskAndFormatCardNumber(creditCard.cardNumber)
                tvCcv.text = creditCard.ccv
                tvExpirationDate.text = creditCard.expirationDate

                root.setOnClickListener {
                    onItemClick(creditCard)
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CreditCard>() {
        override fun areItemsTheSame(oldItem: CreditCard, newItem: CreditCard): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CreditCard, newItem: CreditCard): Boolean {
            return oldItem == newItem
        }
    }
}