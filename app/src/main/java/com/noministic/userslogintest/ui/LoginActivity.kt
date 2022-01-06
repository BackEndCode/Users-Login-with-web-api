package com.noministic.userslogintest.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.noministic.userslogintest.R
import com.noministic.userslogintest.databinding.ActivityLoginBinding
import com.noministic.userslogintest.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        observeViewModel()
        binding.button.setOnClickListener {
            viewModel.login(
                binding.editTextTextPersonName.text.toString(),
                binding.editTextTextEmailAddress.text.toString()
            )
        }
        viewModel.getUser()
    }

    fun observeViewModel() {
        viewModel.loading.observe(this, {
            it.let { binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE }
        })
        viewModel.errorMessage.observe(this, {
            it.let {
                if (it.isNotEmpty())
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
        viewModel.user.observe(this, {
            it.let {
                Toast.makeText(
                    this, getString(R.string.user_logged_in),
                    Toast.LENGTH_LONG
                ).show()
                gotoMainAcitivity()
            }
        })
    }

    fun gotoMainAcitivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

}