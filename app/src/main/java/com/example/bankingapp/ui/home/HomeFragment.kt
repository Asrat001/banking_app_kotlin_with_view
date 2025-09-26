package com.example.bankingapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankingapp.R
import com.example.bankingapp.adaptors.AccountItemAdaptor
import com.example.bankingapp.adaptors.SettingContentAdaptor
import com.example.bankingapp.adaptors.TransactionItemAdaptor
import com.example.bankingapp.databinding.FragmentHomeBinding
import com.example.bankingapp.data.models.AccountItem
import com.example.bankingapp.data.models.TransactionItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var  accountAdaptor: AccountItemAdaptor
    private  lateinit var  transactionAdaptor: TransactionItemAdaptor

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize account adapter with click listener
        accountAdaptor = AccountItemAdaptor { item ->
            Toast.makeText(requireContext(), "Clicked: ${item.accountType}", Toast.LENGTH_SHORT).show()
        }
        //initialize transaction adaptor
        transactionAdaptor = TransactionItemAdaptor { item ->
            Toast.makeText(requireContext(), "Clicked: ${item.description}", Toast.LENGTH_SHORT).show()
        }

        // Setup RecyclerView
        binding.recyclerViewAccounts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewAccounts.adapter = accountAdaptor

        //setup transaction recycler view
        binding.recentTransactionRv.layoutManager = LinearLayoutManager(requireContext())
        binding.recentTransactionRv.adapter = transactionAdaptor

        // Load sample data (replace with your backend call)
        val items = listOf(
            AccountItem("1", "SAVING", "123456789", 1500.00,"12/25"),
            AccountItem("2", "CHECKING", "987654321", 2500.0,"11/24")
        )

        // Sample transaction data
        // Load sample data (replace with your backend call)
        val transactionItems = listOf(
            TransactionItem("1", "2024-10-01", "Grocery Store", 75.50, "GROCERY"),
            TransactionItem("2", "2024-10-02", "Salary", 2000.00, "ISCBILL"),
            TransactionItem("3", "2024-10-03", "Electric Bill", 120.75, "DEBIT"),
            TransactionItem("4", "2024-10-04", "Restaurant", 60.00, "DEBIT"),
            TransactionItem("5", "2024-10-05", "Gym Membership", 45.00, "DEBIT")
        )

        // Submit data to adapter
        accountAdaptor.submitList(items)
        //submit transaction data to adaptor
        transactionAdaptor.submitList(transactionItems.take(2))


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        val window=requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.dark_blue_gray)
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = false
    }

}