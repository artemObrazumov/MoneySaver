package com.borsch_team.moneysaver

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.borsch_team.moneysaver.data.PreferencesManager
import com.borsch_team.moneysaver.databinding.ActivityMainBinding
import com.borsch_team.moneysaver.ui.transaction_editor.TransactionEditorActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SyncronizationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTheme()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[SyncronizationViewModel::class.java]
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        binding.fab.setOnClickListener {
            val intent = Intent(this, TransactionEditorActivity::class.java)
            startActivity(intent)
        }
    }
}