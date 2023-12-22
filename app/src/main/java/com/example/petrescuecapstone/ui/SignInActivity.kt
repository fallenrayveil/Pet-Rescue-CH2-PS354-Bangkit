package com.example.petrescuecapstone.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.Session.SessionManager
import com.example.petrescuecapstone.data.UiState
import com.example.petrescuecapstone.data.UserModel
import com.example.petrescuecapstone.databinding.ActivitySignInBinding
import com.example.petrescuecapstone.viewmodel.SignInViewModel
import com.example.petrescuecapstone.viewmodel.ViewModelFactory

class SignInActivity : AppCompatActivity() {

    private val viewModel by viewModels<SignInViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivitySignInBinding
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        binding.NoAccountButton.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupAction() {
        binding.SigninButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()

            if (email.isEmpty()){
                binding.emailEditText.error = "Email tidak boleh kosong"
                return@setOnClickListener
            }
            if (pass.isEmpty()){
                binding.passwordEditText.error = "password tidak boleh kosong"
                return@setOnClickListener
            }
            if (pass.isNotEmpty()&& email.isNotEmpty()){
                viewModel.login(email, pass).observe(this){result ->
                    if (result!=null){
                        when (result){
                            is UiState.Loading-> {
                                showLoading(isLoading = true)
                            }
                            is UiState.Success-> {
                                AlertDialog.Builder(this).apply {
                                    setTitle("Login Success")
                                    setMessage("Welcome $email")

                                    create()
                                    show()
                                }
                                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                                startActivity(intent)
                                val dataUser = UserModel(
                                    result.data.loginResult.token,
                                    result.data.loginResult.userId.toString(),
                                    result.data.loginResult.name.toString(),
                                    true
                                )

                                showLoading(isLoading = false)
                                sessionManager.setLogin(true)
                                sessionManager.setuser_id(result.data.loginResult.userId!!.toInt())
                                sessionManager.settoken(result.data.loginResult.token)
                                viewModel.saveSession(dataUser)
                                finish()
                                onDestroy()
                            }
                            is UiState.Error ->{
                                showLoading(isLoading = false)
                                showToast(result.error)
                            }
                        }
                    }
                }


            }

        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val message =
            ObjectAnimator.ofFloat(binding.descriptionTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.SigninButton, View.ALPHA, 1f).setDuration(100)
        val noAccount = ObjectAnimator.ofFloat(binding.NoAccountButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login,
                noAccount
            )
            startDelay = 100
        }.start()
    }
}