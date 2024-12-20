package com.example.androidfinalxml.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.androidfinalxml.R
import com.example.androidfinalxml.databinding.FragmentRegisterBinding
import com.example.androidfinalxml.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class RegisterFragment: Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var authViewModel: AuthViewModel

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    private lateinit var progressBar: ProgressBar

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRegisterBinding.bind(view)

        authViewModel = viewModels<AuthViewModel>().value

        progressBar = binding.loadingProgressBar

        emailEditText = binding.emailEditText
        passwordEditText = binding.passwordEditText

        binding.toRegisterButton.setOnClickListener {
            toLoginPage()
        }

        binding.registerButton.setOnClickListener {
            if (emailEditText.text.isEmpty() || passwordEditText.text.isEmpty()) {
                Toast.makeText(requireContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            register(emailEditText.text.toString(), passwordEditText.text.toString())
        }

        emailEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {
                passwordEditText.requestFocus()
                true
            } else {
                false
            }
        }

        passwordEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {
                passwordEditText.clearFocus()
                hideKeyboard(v)
                true
            } else {
                false
            }
        }
    }

    private fun toLoginPage() {
        val viewPager = requireActivity().findViewById<androidx.viewpager2.widget.ViewPager2>(R.id.viewPager)
        viewPager.currentItem = 0
    }

    private fun hideKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun register(email: String, password: String) {
        showLoading()

        lifecycleScope.launch {
            try {
                authViewModel.registerWithEmailAndPasswordSuspend(email, password)
                Toast.makeText(
                    requireContext(),
                    "Account successful created\nNow you can login",
                    Toast.LENGTH_LONG
                ).show()
                toLoginPage()
                authViewModel.checkAuthStatus()
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Error when registration. ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                hideLoading()
            }
        }
    }

}