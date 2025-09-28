package com.example.bankingapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankingapp.R
import com.example.bankingapp.adaptors.AccountItemAdaptor
import com.example.bankingapp.adaptors.SettingContentAdaptor
import com.example.bankingapp.adaptors.TransactionItemAdaptor
import com.example.bankingapp.adaptors.ui_state.AccountUiState
import com.example.bankingapp.adaptors.ui_state.TransactionUiState
import com.example.bankingapp.databinding.FragmentHomeBinding
import com.example.bankingapp.data.models.AccountItem
import com.example.bankingapp.data.models.TransactionItem
import com.example.bankingapp.network.model.ApiResponse
import com.example.bankingapp.network.model.LoginRequest
import com.example.bankingapp.ui.auth.login.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var accountAdaptor: AccountItemAdaptor
    private lateinit var transactionAdaptor: TransactionItemAdaptor

    private val viewModel: HomeViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize account adapter with click listener
        accountAdaptor = AccountItemAdaptor { item ->
            Toast.makeText(requireContext(), "Clicked: ${item.accountType}", Toast.LENGTH_SHORT)
                .show()
        }
        //initialize transaction adaptor
        transactionAdaptor = TransactionItemAdaptor { item ->
            Toast.makeText(requireContext(), "Clicked: ${item.description}", Toast.LENGTH_SHORT)
                .show()
        }

        // Setup RecyclerView
        binding.recyclerViewAccounts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewAccounts.adapter = accountAdaptor

        //setup transaction recycler view
        binding.recentTransactionRv.layoutManager = LinearLayoutManager(requireContext())
        binding.recentTransactionRv.adapter = transactionAdaptor

        viewModel.transactionResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> transactionAdaptor.submitList(listOf(TransactionUiState.Loading))
                is ApiResponse.Failure -> transactionAdaptor.submitList(listOf(TransactionUiState.Error(response.errorMessage)))
                is ApiResponse.Success -> {
                    val items = response.data.content.map { TransactionUiState.Item(it) }
                    transactionAdaptor.submitList(items)
                }
            }
        }

        viewModel.accountResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        accountAdaptor.submitList(listOf(AccountUiState.Loading))
                    }

                    is ApiResponse.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        accountAdaptor.submitList(listOf(AccountUiState.Error(response.errorMessage)))
                    }

                    is ApiResponse.Success -> {
                        val accountIds = response.data.content.map { it.id }
                        val items = response.data.content.map { AccountUiState.Item(it) }
                        val totalBalance=items.sumOf { it.account.balance }
                        binding.totalBalance.text="ETB ${totalBalance}"
                        accountAdaptor.submitList(items)
                        viewModel.getAllAccountTransactions(
                            accountIds,
                            _root_ide_package_.com.example.bankingapp.data.CoroutinesErrorHandler { errorMessage ->
                                Log.d("error",errorMessage)
                                transactionAdaptor.submitList(listOf(TransactionUiState.Error(errorMessage)))
                            }
                        )
                    }
                }
        }


        viewModel.getAccounts(
            0,
            _root_ide_package_.com.example.bankingapp.data.CoroutinesErrorHandler { errorMessage ->
                Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG).show();
            }
        )




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        val window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.dark_blue_gray)
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = false
    }

}