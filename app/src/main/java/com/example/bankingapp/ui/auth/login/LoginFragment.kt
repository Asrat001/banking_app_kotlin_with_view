package com.example.bankingapp.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.appcompat.app.AppCompatActivity
import com.example.bankingapp.R
import com.example.bankingapp.data.TokenViewModel
import com.example.bankingapp.databinding.FragmentLoginBinding
import com.example.bankingapp.network.model.ApiResponse
import com.example.bankingapp.network.model.LoginRequest
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private val viewModel: LoginViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
            if (token != null) {
                navController.navigate(R.id.navigation_home)
            }
        }



        viewModel.loginResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    binding.btnLogin.isEnabled=false
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResponse.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.isEnabled=true
                    Snackbar.make(view, response.errorMessage, Snackbar.LENGTH_LONG).show()
                }
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.isEnabled=true
                    tokenViewModel.saveToken(response.data.accessToken)
                }
            }
        }

        binding.tvRegister.setOnClickListener { navController.navigate(R.id.navigation_register) }


        binding.btnLogin.setOnClickListener {
            val username = binding.usernameField.getText()
            val password = binding.passwordField.getText()
            viewModel.login(
                LoginRequest(username, password),
                _root_ide_package_.com.example.bankingapp.data.CoroutinesErrorHandler { errorMessage ->

                }
            )
        }


    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
