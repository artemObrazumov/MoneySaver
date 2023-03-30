package com.borsch_team.moneysaver.ui.startupActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.borsch_team.moneysaver.MainActivity
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.setupTheme

class ActivityStartup : AppCompatActivity() {

    private lateinit var viewModel: SyncronizationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)
        viewModel = ViewModelProvider(this)[SyncronizationViewModel::class.java]
        viewModel.finished.observe(this) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}