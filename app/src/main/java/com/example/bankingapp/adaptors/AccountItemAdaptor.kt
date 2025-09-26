package com.example.bankingapp.adaptors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.R
import com.example.bankingapp.databinding.AccountItemBinding
import com.example.bankingapp.data.models.AccountItem

class AccountItemAdaptor(
    private val onItemClick: (AccountItem) -> Unit
) : ListAdapter<AccountItem, AccountItemAdaptor.ItemViewHolder>(DiffCallback) {


    object DiffCallback : DiffUtil.ItemCallback<AccountItem>() {
        override fun areItemsTheSame(oldItem: AccountItem, newItem: AccountItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: AccountItem, newItem: AccountItem): Boolean =
            oldItem == newItem
    }

    inner class ItemViewHolder(private val binding: AccountItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AccountItem,isLast: Boolean) = with(binding) {
            divder.visibility=if (isLast) View.GONE else View.VISIBLE
            accountName.text = item.accountType
            accountNumber.text= item.accountNumber
            accountBalance.text = "$${"%.2f".format(item.balance)}"
            accountTypeIcon.setImageResource(getIconForType(item.accountType))
            root.setOnClickListener { onItemClick(item) }
        }

        private fun getIconForType(type: String): Int {
            return when (type.uppercase()) {
                "SAVING" -> R.drawable.checking
                "CHECKING" -> R.drawable.checking
                else -> R.drawable.checking
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = AccountItemBinding.inflate(
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
