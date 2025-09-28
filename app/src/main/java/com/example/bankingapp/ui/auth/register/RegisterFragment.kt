package com.example.bankingapp.ui.auth.register

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.bankingapp.R
import com.example.bankingapp.databinding.FragmentRegisterBinding
import com.example.bankingapp.network.model.ApiResponse
import com.example.bankingapp.network.model.RegistrationRequest
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


        viewModel.registerResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    binding.btnRegister.isEnabled = false
                    binding.progressBar.visibility = View.VISIBLE
                }

                is ApiResponse.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnRegister.isEnabled = true
                    Snackbar.make(requireView(), response.errorMessage, Snackbar.LENGTH_LONG).show()
                }

                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnRegister.isEnabled = true

                    val snackbar = Snackbar.make(
                        requireView(),
                        "You are successfully Registered, Login to Continue",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.view.setBackgroundColor(
                        ContextCompat.getColor(requireContext(), android.R.color.holo_green_dark)
                    )
                    snackbar.show()

                    // Delay navigation slightly to let snackbar show
                    Handler(Looper.getMainLooper()).postDelayed({
                        navController.navigate(R.id.navigation_login)
                    }, 1500)
                }
            }
        }


        binding.btnRegister.setOnClickListener {
            val formData = getFormData()
            if (formData != null) {
                viewModel.register(
                    formData,
                    _root_ide_package_.com.example.bankingapp.data.CoroutinesErrorHandler { errorMessage ->

                    })
            }
        }


        binding.tvLogin.setOnClickListener {
            navController.navigate(R.id.navigation_login)
        }
    }

    fun getFormData(): RegistrationRequest {
        val firstName = binding.firstName.getText()
        val lastName = binding.lastName.getText()
        val userName = binding.usernameField.getText()
        val phoneNumber = binding.phoneNumber.getText()
        val password = binding.passwordField.getText()
        val email=binding.emailFiled.getText()
        return RegistrationRequest(
            firstName,
            lastName,
            phoneNumber,
            userName,
            email,
            password
        )
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }
}
