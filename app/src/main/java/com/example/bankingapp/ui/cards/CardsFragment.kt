package com.example.bankingapp.ui.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankingapp.R
import com.example.bankingapp.adaptors.SettingContentAdaptor
import com.example.bankingapp.adaptors.VisaCardAdaptor
import com.example.bankingapp.data.models.SettingModel
import com.example.bankingapp.data.models.VisaCardModel
import com.example.bankingapp.databinding.FragmentDashboardBinding
import com.example.bankingapp.databinding.FragmentNotificationsBinding
import com.example.bankingapp.databinding.SettingContentBinding
import com.example.bankingapp.databinding.SettingItemBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayoutMediator
import okio.Inflater

class CardsFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null


    private  lateinit var contentFrame: FrameLayout
    private  lateinit var settingBtn: MaterialButton
    private  lateinit var transactionBtn: MaterialButton

    private  lateinit var settingAdaptor: SettingContentAdaptor

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding= FragmentDashboardBinding.inflate(inflater,container,false)
        val root: View = binding.root

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager=binding.viewPagerAccounts
        val tabIndicator=binding.tabDots
        contentFrame=binding.containerToggle
        settingBtn=binding.btnSettings
        transactionBtn=binding.btnTransactions


        settingAdaptor= SettingContentAdaptor{click->  Toast.makeText(requireContext(), "Clicked: ${click.name}", Toast.LENGTH_SHORT).show()}

        val listOfCards=listOf<VisaCardModel>(
            VisaCardModel(
                "1236455******12t3",
                3000.0,
                "9/25"
            ),
            VisaCardModel(
                "1236455******12t3",
                3000.0,
                "9/25"
            )
        )
        viewPager.adapter= VisaCardAdaptor(listOfCards)

        //attach them
        TabLayoutMediator(tabIndicator,viewPager){ _, _ -> }.attach()
        setActiveContent(R.layout.setting_content)
        settingBtn.setOnClickListener {
            setActiveContent(R.layout.setting_content)
        }


    }

    private fun setActiveContent(id:Int){
        contentFrame.removeAllViews()
        when(id){
            R.layout.setting_content->{
                val settingBinding= SettingContentBinding.inflate(layoutInflater,contentFrame,true)
                settingBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.acttive_action_button)
                transactionBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.action_btn_bg)

                // Initialize RecyclerView
                val recyclerView = settingBinding.settingRv
                recyclerView.layoutManager= LinearLayoutManager(requireContext())
                recyclerView.adapter = settingAdaptor
                val settingItems = listOf(
                    SettingModel(1,"receipt","View Statements"),
                    SettingModel(2,"changePin","Change Pin"),
                    SettingModel(3,"removeCard","Remove Cards")
                )
                settingAdaptor.submitList(settingItems)


            }
        }
    }

    override fun onResume() {
        super.onResume()
        val window=requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.light_gray)
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = true
    }

}