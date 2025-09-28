package com.example.bankingapp.adaptors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.R
import com.example.bankingapp.adaptors.ui_state.TransactionUiState
import com.example.bankingapp.databinding.TransactionItemBinding
import com.example.bankingapp.data.models.TransactionItem
import com.example.bankingapp.databinding.ErrorItemBinding
import com.example.bankingapp.databinding.LoadingItemBinding
import com.example.bankingapp.network.model.TransactionResponse
import com.example.bankingapp.network.model.TransactionType


class TransactionItemAdaptor(
    private val onItemClick: (TransactionResponse) -> Unit
) : ListAdapter<TransactionUiState, RecyclerView.ViewHolder>(DiffCallback) {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
        private const val VIEW_TYPE_ERROR = 2
    }

    object DiffCallback : DiffUtil.ItemCallback<TransactionUiState>() {
        override fun areItemsTheSame(oldItem: TransactionUiState, newItem: TransactionUiState): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: TransactionUiState, newItem: TransactionUiState): Boolean =
            oldItem == newItem
    }

    inner class ItemViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TransactionResponse, isLast: Boolean) = with(binding) {
            divder.visibility = if (isLast) View.GONE else View.VISIBLE
            transactionName.text = item.type.name
            transactionAmount.text = "- $${"%.2f".format(item.amount)}"
            transactionTypeIcon.setImageResource(getIconForType(item.type))
            root.setOnClickListener { onItemClick(item) }
        }

        private fun getIconForType(type: TransactionType): Int {
            return when (type) {
                TransactionType.FUND_TRANSFER,
                TransactionType.TELLER_TRANSFER,
                TransactionType.ATM_WITHDRAWAL,
                TransactionType.BILL_PAYMENT,
                TransactionType.ACCESS_FEE -> R.drawable.found_transfer
                TransactionType.TELLER_DEPOSIT,
                TransactionType.REFUND,
                TransactionType.INTEREST_EARNED,
                TransactionType.LOAN_PAYMENT,
                TransactionType.SALARY -> R.drawable.checking
                TransactionType.PURCHASE,
                TransactionType.GROCERY -> R.drawable.cart
                TransactionType.CARD_PAYMENT -> R.drawable.doc_outlin
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
            is TransactionUiState.Item -> VIEW_TYPE_ITEM
            is TransactionUiState.Loading -> VIEW_TYPE_LOADING
            is TransactionUiState.Error -> VIEW_TYPE_ERROR
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding = TransactionItemBinding.inflate(
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
            is TransactionUiState.Item -> {
                val isLastItem = position == itemCount - 1
                (holder as ItemViewHolder).bind(item.transaction, isLastItem)
            }
            is TransactionUiState.Loading -> Unit
            is TransactionUiState.Error -> (holder as ErrorViewHolder).bind(item.message)
        }
    }
}
