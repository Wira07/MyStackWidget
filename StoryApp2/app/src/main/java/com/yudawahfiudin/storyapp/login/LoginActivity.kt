package com.yudawahfiudin.storyapp.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.yudawahfiudin.storyapp.R
import com.yudawahfiudin.storyapp.databinding.ActivityLoginBinding
import com.yudawahfiudin.storyapp.data.Resource
import com.yudawahfiudin.storyapp.remote.User
import com.yudawahfiudin.storyapp.model.ViewModelFactory
import com.yudawahfiudin.storyapp.register.RegisterActivity
import com.yudawahfiudin.storyapp.view.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
        moveToRegister()
        setAnimation()
    }

    private fun setupView() {
        binding.loginButton.setOnClickListener {
            if (valid()) {
                val email = binding.edLoginEmail.text.toString()
                val password = binding.edLoginPassword.text.toString()
                loginViewModel.userLogin(email, password).observe(this) {
                    when (it) {
                        is Resource.Success-> {
                            showLoad(false)
                            val response = it.data
                            saveUserData(
                                User(
                                    response.loginResult?.name.toString(),
                                    response.loginResult?.token.toString(),
                                    true
                                )
                            )
                            startActivity(Intent(this, MainActivity::class.java))
                            finishAffinity()
                        }
                        is Resource.Loading -> showLoad(true)
                        is Resource.Error -> {
                            Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                            showLoad(false)
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

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }



    private fun valid() =
        binding.edLoginEmail.error == null && binding.edLoginPassword.error == null && !binding.edLoginEmail.text.isNullOrEmpty() && !binding.edLoginPassword.text.isNullOrEmpty()

    private fun saveUserData(user: User) {
        loginViewModel.saveUser(user)
    }

    private fun setAnimation() {
        val appIcon = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(700)
        val tvEmail = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(700)
        val etEmail = ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(700)
        val tvPass = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(700)
        val etPass = ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(700)
        val btnLogin = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(700)
        val btnRegister = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(700)
        val tvHaveAcc = ObjectAnimator.ofFloat(binding.textViewHaveAccount, View.ALPHA, 1f).setDuration(700)
        val crYuda = ObjectAnimator.ofFloat(binding.copyrightTextView, View.ALPHA, 1f).setDuration(700)

        val textAnim = AnimatorSet().apply {
            playTogether(tvEmail, tvPass)
        }
        val layoutAnim = AnimatorSet().apply {
            playTogether(etPass, etEmail)
        }

        AnimatorSet().apply {
            playSequentially(
                appIcon,
                textAnim,
                layoutAnim,
                btnLogin,
                tvHaveAcc,
                btnRegister,
                crYuda


            )
            start()
        }
    }

    private fun moveToRegister() {
        binding.apply {
            registerButton.setOnClickListener {
                Intent(this@LoginActivity, RegisterActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }
        }
    }

    override fun onBackPressed() {
        val finish = Intent(Intent.ACTION_MAIN)
        finish.addCategory(Intent.CATEGORY_HOME)
        finish.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(finish)
    }

    private fun showLoad(isLoad: Boolean) {
        if (isLoad){
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            binding.progressBar.visibility = View.GONE
        }
    }
}