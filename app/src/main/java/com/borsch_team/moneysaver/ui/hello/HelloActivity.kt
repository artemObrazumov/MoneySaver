package com.borsch_team.moneysaver.ui.hello

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.MainActivity
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.data.PreferencesManager
import com.borsch_team.moneysaver.data.api.API
import com.borsch_team.moneysaver.databinding.ActivityHelloBinding

class HelloActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelloBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHelloBinding.inflate(layoutInflater)

        binding.btnSignIn.setOnClickListener {
            val userName = binding.inputUserName.text.toString()
            if(userName != ""){
                App.preferencesManager.saveUsername(userName)
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                Toast.makeText(applicationContext, "Заполните поле!", Toast.LENGTH_SHORT).show()
            }
        }

        setContentView(binding.root)
    }
}