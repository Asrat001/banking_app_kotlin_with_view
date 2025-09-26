package com.example.bankingapp.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.R
import com.example.bankingapp.data.models.SettingModel
import com.example.bankingapp.databinding.SettingItemBinding


class SettingContentAdaptor(
    private val onItemClick: (SettingModel) -> Unit
) : ListAdapter<SettingModel, SettingContentAdaptor.ItemViewHolder>(DiffCallback) {



    object DiffCallback : DiffUtil.ItemCallback<SettingModel>() {
        override fun areItemsTheSame(oldItem: SettingModel, newItem: SettingModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SettingModel, newItem: SettingModel): Boolean =
            oldItem == newItem
    }

    inner class ItemViewHolder(private val binding: SettingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SettingModel,isLast: Boolean) = with(binding) {
            divder.visibility=if (isLast) View.GONE else View.VISIBLE
            settingName.text= item.name
            icon.setImageResource(getIconForType(item.iconName))
            root.setOnClickListener { onItemClick(item) }
        }

        private fun getIconForType(type: String): Int {
            return when (type) {
                "receipt" -> R.drawable.doc_outlin
                "changePin" -> R.drawable.chenege_pin
                "removeCard"->R.drawable.remove_card
                else -> R.drawable.doc_outlin
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SettingItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        val isLastItem = position == itemCount - 1
        holder.bind(item, isLastItem)
    }
}
