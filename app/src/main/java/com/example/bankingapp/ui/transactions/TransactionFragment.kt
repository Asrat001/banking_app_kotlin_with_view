package com.example.bankingapp.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankingapp.R
import com.example.bankingapp.adaptors.TransactionItemAdaptor
import com.example.bankingapp.data.models.TransactionItem
import com.example.bankingapp.databinding.FragmentNotificationsBinding

class TransactionFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private  lateinit var  transactionAdaptor: TransactionItemAdaptor
    private  lateinit var  yestransactionAdaptor: TransactionItemAdaptor
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initialize transaction adaptor
        transactionAdaptor = TransactionItemAdaptor { item ->
            Toast.makeText(requireContext(), "Clicked: ${item.description}", Toast.LENGTH_SHORT).show()
        }

        //initialize transaction adaptor
        yestransactionAdaptor= TransactionItemAdaptor { item ->
            Toast.makeText(requireContext(), "Clicked: ${item.description}", Toast.LENGTH_SHORT).show()
        }
        //setup transaction recycler view
        binding.rvTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTransactions.adapter = transactionAdaptor
        //setup transaction recycler view
        binding.rvYesterdayTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvYesterdayTransactions.adapter = yestransactionAdaptor


//        todaylist
        val transactionItems = listOf(
            TransactionItem("1", "2024-10-01", "Grocery Store", 75.50, "GROCERY"),
            TransactionItem("2", "2024-10-02", "Salary", 2000.00, "ISCBILL"),
            TransactionItem("3", "2024-10-03", "Electric Bill", 120.75, "DEBIT"),
            TransactionItem("4", "2024-10-04", "Restaurant", 60.00, "DEBIT"),
            TransactionItem("5", "2024-10-05", "Gym Membership", 45.00, "DEBIT")
        )
        //yesterday list

        val yeserdatTransactionItems = listOf(
            TransactionItem("1", "2024-10-01", "Grocery Store", 75.50, "GROCERY"),
            TransactionItem("2", "2024-10-02", "Salary", 2000.00, "ISCBILL"),
            TransactionItem("3", "2024-10-03", "Electric Bill", 120.75, "DEBIT"),
            TransactionItem("4", "2024-10-04", "Restaurant", 60.00, "DEBIT"),
            TransactionItem("5", "2024-10-05", "Gym Membership", 45.00, "DEBIT")
        )

        transactionAdaptor.submitList(transactionItems)
        yestransactionAdaptor.submitList(yeserdatTransactionItems)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        val window=requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.light_gray)
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = true
    }

}