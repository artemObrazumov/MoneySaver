package com.borsch_team.moneysaver.ui.category_editor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.data.PreferencesManager
import com.borsch_team.moneysaver.data.models.TransactionCategory
import com.borsch_team.moneysaver.databinding.ActivityCategoryEditorBinding
import com.borsch_team.moneysaver.setupTheme
import com.borsch_team.moneysaver.ui.category_select.CategorySelectViewModel
import com.borsch_team.moneysaver.ui.dialog.CompletedDialog

class CategoryEditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryEditorBinding
    private lateinit var viewModel: CategoryEditorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTheme()
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryEditorBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CategoryEditorViewModel::class.java]
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
            arrayOf("Расходам", "Доходам")
        )
        adapter.setDropDownViewResource(R.layout.spinner_item)
        binding.typeSelect.adapter = adapter
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Добавление категории"
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
                finish()
                onBackPressed()
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun onEditingFinished() {
        val category = TransactionCategory(
            null,
            binding.titleInput.text.toString(),
            binding.typeSelect.selectedItemPosition == 0
        )
        viewModel.upsertCategory(category)
        CompletedDialog("Категория загружена"){
            onBackPressed()
        }.show(supportFragmentManager, "completed")
    }
}