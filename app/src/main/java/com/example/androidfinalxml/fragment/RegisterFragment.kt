package com.example.androidfinalxml.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.androidfinalxml.R
import com.example.androidfinalxml.databinding.FragmentRegisterBinding
import com.example.androidfinalxml.viewmodel.AuthViewModel

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

        progressBar = binding.loadingProgressBar

        emailEditText = binding.emailEditText
        passwordEditText = binding.passwordEditText

        binding.toLoginButton.setOnClickListener {
            val viewPager = requireActivity().findViewById<androidx.viewpager2.widget.ViewPager2>(R.id.viewPager)
            viewPager.currentItem = 0
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

    private fun hideKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}