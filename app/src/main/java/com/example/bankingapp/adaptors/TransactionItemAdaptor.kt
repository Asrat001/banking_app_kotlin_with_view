package com.example.bankingapp.adaptors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.R
import com.example.bankingapp.databinding.TransactionItemBinding
import com.example.bankingapp.data.models.TransactionItem


class TransactionItemAdaptor(
    private val onItemClick: (TransactionItem) -> Unit
) : ListAdapter<TransactionItem, TransactionItemAdaptor.ItemViewHolder>(DiffCallback) {
    


    object DiffCallback : DiffUtil.ItemCallback<TransactionItem>() {
        override fun areItemsTheSame(oldItem: TransactionItem, newItem: TransactionItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TransactionItem, newItem: TransactionItem): Boolean =
            oldItem == newItem
    }

    inner class ItemViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TransactionItem,isLast: Boolean) = with(binding) {
            divder.visibility=if (isLast) View.GONE else View.VISIBLE
            transactionName.text= item.type
            transactionAmount.text = "- $${"%.2f".format(item.amount)}"
            transactionTypeIcon.setImageResource(getIconForType(item.type))
            root.setOnClickListener { onItemClick(item) }
        }

        private fun getIconForType(type: String): Int {
            return when (type.uppercase()) {
                "GROCERY" -> R.drawable.cart
                "ISCBILL" -> R.drawable.doc_outlin
                else -> R.drawable.doc_outlin
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = TransactionItemBinding.inflate(
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
