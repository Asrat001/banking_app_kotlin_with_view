package com.example.bankingapp.adaptors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.R
import com.example.bankingapp.adaptors.ui_state.AccountUiState
import com.example.bankingapp.databinding.AccountItemBinding
import com.example.bankingapp.data.models.AccountModel
import com.example.bankingapp.databinding.ErrorItemBinding
import com.example.bankingapp.databinding.LoadingItemBinding

class AccountItemAdaptor(
    private val onItemClick: (AccountModel) -> Unit
) : ListAdapter<AccountUiState, RecyclerView.ViewHolder>(DiffCallback) {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
        private const val VIEW_TYPE_ERROR = 2
    }

    object DiffCallback : DiffUtil.ItemCallback<AccountUiState>() {
        override fun areItemsTheSame(oldItem: AccountUiState, newItem: AccountUiState): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: AccountUiState, newItem: AccountUiState): Boolean =
            oldItem == newItem
    }

    inner class ItemViewHolder(private val binding: AccountItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AccountModel, isLast: Boolean) = with(binding) {
            divder.visibility = if (isLast) View.GONE else View.VISIBLE
            accountName.text = item.accountType
            accountNumber.text = item.accountNumber
            accountBalance.text = "$${"%.2f".format(item.balance)}"
            accountTypeIcon.setImageResource(getIconForType(item.accountType))
            root.setOnClickListener { onItemClick(item) }
        }

        private fun getIconForType(type: String): Int {
            return when (type.uppercase()) {
                "SAVINGS" -> R.drawable.checking
                "CHECKING" -> R.drawable.checking
                else -> R.drawable.checking
            }
        }
    }

    inner class LoadingViewHolder(private val binding: LoadingItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ErrorViewHolder(private val binding: ErrorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: String) {
            binding.errorText.text = message
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is AccountUiState.Item -> VIEW_TYPE_ITEM
            is AccountUiState.Loading -> VIEW_TYPE_LOADING
            is AccountUiState.Error -> VIEW_TYPE_ERROR
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding = AccountItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ItemViewHolder(binding)
            }
            VIEW_TYPE_LOADING -> {
                val binding = LoadingItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                LoadingViewHolder(binding)
            }
            VIEW_TYPE_ERROR -> {
                val binding = ErrorItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ErrorViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is AccountUiState.Item -> {
                val isLastItem = position == itemCount - 1
                (holder as ItemViewHolder).bind(item.account, isLastItem)
            }
            is AccountUiState.Loading -> Unit
            is AccountUiState.Error -> (holder as ErrorViewHolder).bind(item.message)
        }
    }
}
