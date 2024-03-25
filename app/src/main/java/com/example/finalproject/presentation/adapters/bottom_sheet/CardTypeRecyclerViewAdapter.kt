package com.example.finalproject.presentation.adapters.bottom_sheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ItemCardTypeLayoutBinding
import com.example.finalproject.presentation.model.bottom_sheets.NewCardType

class CardTypeRecyclerViewAdapter(private val onCardTypeClick: (NewCardType) -> Unit) : ListAdapter<NewCardType, CardTypeRecyclerViewAdapter.CardTypeViewHolder>(DiffUtilCallBack) {

    companion object DiffUtilCallBack : DiffUtil.ItemCallback<NewCardType>(){
        override fun areItemsTheSame(oldItem: NewCardType, newItem: NewCardType): Boolean {
            return oldItem.path == newItem.path
        }

        override fun areContentsTheSame(oldItem: NewCardType, newItem: NewCardType): Boolean {
            return oldItem == newItem
        }
    }

    inner class CardTypeViewHolder(private val binding : ItemCardTypeLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card : NewCardType) {
            with(binding) {

                icCard.setImageResource(card.path)

                root.setOnClickListener {
                    onCardTypeClick(card)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTypeViewHolder {
        return CardTypeViewHolder(ItemCardTypeLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CardTypeViewHolder, position: Int) {
        val cardType = getItem(position)
        holder.bind(cardType)
    }
}