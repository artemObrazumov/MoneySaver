package com.borsch_team.moneysaver.ui.auth.sign_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.borsch_team.moneysaver.MainActivity
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.databinding.ActivityAuthSignUpBinding
import com.borsch_team.moneysaver.setupTheme

class AuthSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthSignUpBinding
    private lateinit var viewModel: AuthSignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTheme()
        super.onCreate(savedInstanceState)

        binding = ActivityAuthSignUpBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AuthSignUpViewModel::class.java]

        binding.btnSignUp.setOnClickListener {
            val email = "" + binding.inputEmail.text
            val password = "" + binding.inputPassword.text
            val repeatPassword = "" + binding.inputRepeatPassword

            if(email != "" && password != "" && repeatPassword != ""){
                viewModel.authResultData.observe(this){
                    if(it.isSuccessful){
                        startActivity(Intent(this, MainActivity::class.java).apply {
                            putExtra("afterLogin", true)
                        })
                    }else{
                        Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
                    }
                }
                viewModel.signUpWithEmail(email, password)
            }else{
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
        setContentView(binding.root)
    }
}