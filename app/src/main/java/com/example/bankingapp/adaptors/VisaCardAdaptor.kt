package com.example.bankingapp.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.data.models.VisaCardModel
import com.example.bankingapp.databinding.VisaCardLayoutBinding

class VisaCardAdaptor(private val items: List<VisaCardModel>) :
    RecyclerView.Adapter<VisaCardAdaptor.CardViewHolder>() {

    inner class CardViewHolder(val binding: VisaCardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = VisaCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val  item=items[position]
        holder.binding.cardNumber.text=item.cardNumber
        holder.binding.expiryDate.text=item.expireDate
        holder.binding.balanceAmount.text="$ ${item.balance}"
    }

    override fun getItemCount() = items.size
}
