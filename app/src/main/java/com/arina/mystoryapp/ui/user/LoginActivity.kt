package com.arina.mystoryapp.ui.user

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.arina.mystoryapp.R
import com.arina.mystoryapp.data.model.User
import com.arina.mystoryapp.data.networking.response.LoginResponse
import com.arina.mystoryapp.data.source.Resource
import com.arina.mystoryapp.databinding.ActivityLoginBinding
import com.arina.mystoryapp.ui.MainActivity
import com.arina.mystoryapp.viewmodel.UserViewModel
import com.arina.mystoryapp.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupLAVModel()
        setupAction()
        setAnimation()
    }


    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            if (valid()) {
                val email = binding.etEmail.text.toString()
                val password = binding.etPass.text.toString()

                userViewModel.userLogin(email, password).observe(this) { result ->
                    when (result) {
                        is Resource.Success -> handleLoginSuccess(result.data)
                        is Resource.Loading -> showProgressIndicator(true)
                        is Resource.Error -> handleLoginError(result.error)
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.check_input),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun handleLoginSuccess(data: LoginResponse) {
        showProgressIndicator(false)

        val user = User(
            data.loginResult?.name.toString(),
            data.loginResult?.token.toString(),
            true
        )

        saveUserData(user)

        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    private fun handleLoginError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        showProgressIndicator(false)
    }

    //Animation
    private fun setAnimation() {
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(300)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.etEmailLayout, View.ALPHA, 1f).setDuration(300)
        val emailEditText = ObjectAnimator.ofFloat(binding.etEmail, View.ALPHA, 1f).setDuration(300)

        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(300)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.etPassLayout, View.ALPHA, 1f).setDuration(300)
        val passwordEditText = ObjectAnimator.ofFloat(binding.etPass, View.ALPHA, 1f).setDuration(300)

        val loginButton = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(300)

        val dontHaveAcc = ObjectAnimator.ofFloat(binding.tvDontHaveAcc, View.ALPHA, 1f).setDuration(300)
        val registerButton = ObjectAnimator.ofFloat(binding.tvSignup, View.ALPHA, 1f).setDuration(300)

        val together = AnimatorSet().apply {
            playTogether(dontHaveAcc, registerButton)
        }

        AnimatorSet().apply {
            playSequentially(emailTextView, emailEditTextLayout, emailEditText, passwordTextView, passwordEditTextLayout, passwordEditText, loginButton, together)
            start()
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }


    private fun saveUserData(user: User) {
        userViewModel.saveUser(user)
    }

    private fun setupLAVModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    private fun showProgressIndicator(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun valid() =
        binding.etEmail.error == null && binding.etPass.error == null && !binding.etEmail.text.isNullOrEmpty() && !binding.etPass.text.isNullOrEmpty()

}