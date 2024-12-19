package com.example.androidfinalxml.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.androidfinalxml.R
import com.example.androidfinalxml.activity.HomeActivity
import com.example.androidfinalxml.databinding.FragmentLoginBinding
import com.example.androidfinalxml.viewmodel.AuthViewModel
import kotlinx.coroutines.launch


class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var authViewModel: AuthViewModel

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind(view)

        progressBar = binding.loadingProgressBar

        emailEditText = binding.emailEditText
        passwordEditText = binding.passwordEditText

        authViewModel = viewModels<AuthViewModel>().value

        authViewModel.isLoggedIn.observe(viewLifecycleOwner, Observer { isLoggedIn ->
            if (isLoggedIn) {
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })

        binding.loginButton.setOnClickListener {
            if (emailEditText.text.isEmpty() || passwordEditText.text.isEmpty()) {
                Toast.makeText(requireContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            login(emailEditText.text.toString(), passwordEditText.text.toString())
        }

        binding.toRegisterButton.setOnClickListener {
            val viewPager = requireActivity().findViewById<androidx.viewpager2.widget.ViewPager2>(R.id.viewPager)
            viewPager.currentItem = 1
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

    private fun hideKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun login(email: String, password: String) {
        showLoading()

        lifecycleScope.launch {
            try {
                authViewModel.signInWithEmailAndPasswordSuspend(email, password)
                Toast.makeText(
                    requireContext(),
                    "Success sign in",
                    Toast.LENGTH_LONG
                ).show()
                authViewModel.checkAuthStatus()
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                hideLoading()
            }
        }
    }


}
