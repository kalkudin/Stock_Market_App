package com.example.finalproject.presentation.adapters.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemProfileCreditCardLayoutBinding
import com.example.finalproject.presentation.mapper.funds.maskAndFormatCardNumber
import com.example.finalproject.presentation.model.funds.CreditCard

class CreditCardViewPagerAdapter(private val onDeleteCardClick: (CreditCard) -> Unit) : ListAdapter<CreditCard, CreditCardViewPagerAdapter.ProfileCardViewHolder>(DiffCallBack){

    companion object DiffCallBack : DiffUtil.ItemCallback<CreditCard>() {
        override fun areItemsTheSame(oldItem: CreditCard, newItem: CreditCard): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CreditCard, newItem: CreditCard): Boolean {
            return oldItem == newItem
        }

    }

    inner class ProfileCardViewHolder(private val binding : ItemProfileCreditCardLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card : CreditCard) {
            with(binding) {

                tvCardNumber.text = maskAndFormatCardNumber(cardNumber = card.cardNumber)
                tvCcv.text = card.ccv
                tvExpiryDate.text = card.expirationDate
                ivCardType

                when(card.cardType) {
                    CreditCard.CardType.VISA -> {
                        cardContainer.setImageResource(R.drawable.style_card_visa)
                        ivCardType.setImageResource(R.drawable.ic_visa)
                    }

                    CreditCard.CardType.MASTER_CARD -> {
                        ivCardType.setImageResource(R.drawable.ic_master_card)
                        cardContainer.setImageResource(R.drawable.style_card_mastercard)
                    }

                    else -> {
                        ivCardType.setImageResource(R.drawable.ic_blank_card_icon)
                        cardContainer.setImageResource(R.drawable.style_card_type_unknown)
                    }
                }

                btnRemoveCard.setOnClickListener {
                    onDeleteCardClick(card)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileCardViewHolder {
        val binding = ItemProfileCreditCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileCardViewHolder, position: Int) {
        val creditCard = getItem(position)
        holder.bind(creditCard)
    }
}