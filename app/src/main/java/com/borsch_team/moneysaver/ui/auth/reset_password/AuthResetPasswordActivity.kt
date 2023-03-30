package com.borsch_team.moneysaver.ui.auth.reset_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.borsch_team.moneysaver.MainActivity
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.databinding.ActivityAuthResetPasswordBinding
import com.borsch_team.moneysaver.setupTheme
import com.borsch_team.moneysaver.ui.auth.sign_in.AuthSignInActivity
import javax.security.auth.login.LoginException

class AuthResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthResetPasswordBinding
    private lateinit var viewModel: AuthResetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTheme()
        super.onCreate(savedInstanceState)
        binding = ActivityAuthResetPasswordBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AuthResetPasswordViewModel::class.java]

        binding.btnResetPassword.setOnClickListener {
            val email = "" + binding.inputEmail.text
            if(email != ""){
                viewModel.authResultData.observe(this){
                    if(it.isSuccessful){
                        startActivity(Intent(this, AuthSignInActivity::class.java))
                        Toast.makeText(this, "Письмо отправлено", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
                    }
                }
                viewModel.resetPassword(email)
            }else{
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        setContentView(binding.root)
    }
}