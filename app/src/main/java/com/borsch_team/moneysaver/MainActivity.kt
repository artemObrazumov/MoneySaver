package com.borsch_team.moneysaver

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.borsch_team.moneysaver.databinding.ActivityMainBinding
import com.borsch_team.moneysaver.ui.hello.HelloActivity
import com.borsch_team.moneysaver.ui.dialog.CompletedDialog
import com.borsch_team.moneysaver.ui.dialog.WarningDialog
import com.borsch_team.moneysaver.ui.startupActivity.SyncronizationViewModel
import com.borsch_team.moneysaver.ui.transaction_editor.TransactionEditorActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTheme()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        binding.fab.setOnClickListener {
            val intent = Intent(this, TransactionEditorActivity::class.java)
            startActivity(intent)
        }

        if (intent.getBooleanExtra("afterLogin", false)) {
            CompletedDialog("Вход в аккаунт выполнен. Для успешной синхронизации с сервером перезагрузите приложение") {}
                .show(supportFragmentManager, "afterLogin")
        }
    }

    override fun onStart() {
        super.onStart()
        /*if(PreferencesManager.getUsername(applicationContext) == null){
            startActivity(Intent(this, HelloActivity::class.java))
        }*/
    }
}