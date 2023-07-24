package com.arina.mystoryapp.ui.user

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.arina.mystoryapp.R
import com.arina.mystoryapp.data.source.Resource
import com.arina.mystoryapp.databinding.ActivityRegisterBinding
import com.arina.mystoryapp.viewmodel.UserViewModel
import com.arina.mystoryapp.viewmodel.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupRAVModel()
        setupAction()
        setAnimation()
    }

    private fun setupAction() {
        binding.tvLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnSignup.setOnClickListener{
            if (valid()) {
                val name = binding.etName.text.toString()
                val email = binding.etEmail.text.toString()
                val password = binding.etPass.text.toString()
                userViewModel.userRegister(name, email, password).observe(this) {
                    when (it) {
                        is Resource.Success -> {
                            showProgressIndicator(false)
                            Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finishAffinity()
                        }
                        is Resource.Loading -> showProgressIndicator(true)
                        is Resource.Error -> {
                            Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                            showProgressIndicator(false)
                        }
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
    }

    private fun valid() =
        binding.etEmail.error == null && binding.etPass.error == null && binding.etName.error == null && !binding.etEmail.text.isNullOrEmpty() && !binding.etPass.text.isNullOrEmpty() && !binding.etName.text.isNullOrEmpty()


    //animation
    private fun setAnimation() {
        val registerTextView = ObjectAnimator.ofFloat(binding.tvWelcomeBack, View.ALPHA, 1f).setDuration(300)

        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(300)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val nameEditText = ObjectAnimator.ofFloat(binding.etName, View.ALPHA, 1f).setDuration(300)

        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(300)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.etEmailLayout, View.ALPHA, 1f).setDuration(300)
        val emailEditText = ObjectAnimator.ofFloat(binding.etEmail, View.ALPHA, 1f).setDuration(300)

        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(300)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.etPassLayout, View.ALPHA, 1f).setDuration(300)
        val passwordEditText = ObjectAnimator.ofFloat(binding.etPass, View.ALPHA, 1f).setDuration(300)

        val loginButton = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1f).setDuration(300)

        val haveAcc = ObjectAnimator.ofFloat(binding.tvHaveAcc, View.ALPHA, 1f).setDuration(300)
        val backButton = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(300)

        val together = AnimatorSet().apply {
            playTogether(haveAcc, backButton)
        }

        AnimatorSet().apply {
            playSequentially(
                registerTextView,
                nameTextView, nameEditTextLayout, nameEditText,
                emailTextView, emailEditTextLayout, emailEditText,
                passwordTextView, passwordEditTextLayout, passwordEditText,
                loginButton,
                together
            )
            start()
        }
    }

    private fun setupRAVModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    private fun showProgressIndicator(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}