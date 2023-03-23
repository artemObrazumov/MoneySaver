package com.borsch_team.moneysaver.ui.bill_editor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.databinding.ActivityBillEditorBinding

class BillEditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBillEditorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Добавление счёта"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.editor_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.finish -> {
                onEditingFinished()
                true
            }
            else -> {
                onBackPressed()
                super.onOptionsItemSelected(item);
            }
        }
    }

    private fun onEditingFinished() {

    }
}