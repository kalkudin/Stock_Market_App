package com.example.finalproject.presentation.adapters.funds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemCreditCardLayoutBinding
import com.example.finalproject.presentation.mapper.funds.maskAndFormatCardNumber
import com.example.finalproject.presentation.model.funds.CreditCard

class CreditCardRecyclerAdapter (private val onItemClick: (CreditCard) -> Unit): ListAdapter<CreditCard, CreditCardRecyclerAdapter.CreditCardViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<CreditCard>() {
        override fun areItemsTheSame(oldItem: CreditCard, newItem: CreditCard): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CreditCard, newItem: CreditCard): Boolean {
            return oldItem == newItem
        }
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

                when(creditCard.cardType) {
                    CreditCard.CardType.VISA -> {
                        icContainer.setImageResource(R.drawable.style_card_visa)
                        ivCardType.setImageResource(R.drawable.ic_visa_icon)
                    }
                    CreditCard.CardType.MASTER_CARD -> {
                        icContainer.setImageResource(R.drawable.style_card_mastercard)
                        ivCardType.setImageResource(R.drawable.ic_master_card_icon)
                    }
                    CreditCard.CardType.UNKNOWN -> {
                        icContainer.setImageResource(R.drawable.style_card_type_unknown)
                        ivCardType.setImageResource(R.drawable.ic_blank_card_icon)
                    }
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditCardViewHolder {
        val binding = ItemCreditCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CreditCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CreditCardViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}