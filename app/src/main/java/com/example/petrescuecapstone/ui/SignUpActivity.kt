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
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.petrescuecapstone.R
import com.example.petrescuecapstone.data.UiState
import com.example.petrescuecapstone.databinding.ActivitySignUpBinding
import com.example.petrescuecapstone.viewmodel.SignUpViewModel
import com.example.petrescuecapstone.viewmodel.ViewModelFactory

class SignUpActivity : AppCompatActivity() {
    private  val viewModel by viewModels<SignUpViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.HaveAccountButton.setOnClickListener{
            val intent = Intent(this,SignInActivity::class.java)
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

    private fun setupAction() {
        binding.SignupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()
            val name = binding.nameEditText.text.toString()
            if (name.isEmpty()){
                binding.nameEditText.error = "Nama tidak boleh kosong"
                return@setOnClickListener
            }
            if (email.isEmpty()){
                binding.emailEditText.error = "Email tidak boleh kosong"
                return@setOnClickListener
            }
            if (pass.isEmpty()){
                binding.passwordEditText.error = "Password tidak boleh kosong"
                return@setOnClickListener
            }
            if (name.isNotEmpty()&& pass.isNotEmpty()&& email.isNotEmpty()){
                viewModel.signup(name,email,pass).observe(this){result->
                    when(result){
                        is UiState.Loading ->{
                            showLoading(true)
                        }
                        is UiState.Success ->{
                            AlertDialog.Builder(this).apply {
                                setTitle("Registrasi Berhasil")
                                setMessage("Akun dengan $email sudah jadi nih. Yuk, login dahulu")
                                setPositiveButton("Lanjut") { _, _ ->
                                    val intent = Intent(context, SignInActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                                create()
                                show()
                            }
                            startActivity(Intent(this, SignInActivity::class.java))
                        }
                        is UiState.Error ->{
                            showLoading(false)
                            showToast(result.error)
                        }

                    }
                }

            }

        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar2.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.SignupButton, View.ALPHA, 1f).setDuration(100)
        val noAccount = ObjectAnimator.ofFloat(binding.HaveAccountButton,View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup,
                noAccount
            )
            startDelay = 100
        }.start()
    }
}