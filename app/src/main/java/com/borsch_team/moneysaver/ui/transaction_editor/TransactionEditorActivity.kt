package com.borsch_team.moneysaver.ui.transaction_editor

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import androidx.lifecycle.ViewModelProvider
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.data.models.MoneyTransaction
import com.borsch_team.moneysaver.data.models.TransactionCategory
import com.borsch_team.moneysaver.databinding.ActivityTransactionEditorBinding
import com.borsch_team.moneysaver.ui.category_select.CategorySelectFragment
import com.borsch_team.moneysaver.ui.qr_scanner.QrScannerActivity
import com.google.android.material.snackbar.Snackbar

@ExperimentalGetImage class TransactionEditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionEditorBinding
    private lateinit var viewModel: TransactionEditorViewModel
    private var selectedCategory: TransactionCategory? = null
    private var selectedBillId: Long? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransactionEditorBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[TransactionEditorViewModel::class.java]

        var textQr = ""
        textQr += intent.getStringExtra("text_qr")
        val arr = textQr.split("&")
        var money =  ""
        for(i in arr.indices){
            if(arr[i][0] == 's'){
                money = arr[i].drop(2)
            }
        }

        if(textQr != ""){
            binding.etMoney.setText(money)
        }

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Добавление операции"

        binding.tvScanQr.setOnClickListener {
            startActivity(Intent(this, QrScannerActivity::class.java))
        }
        binding.category.setOnClickListener {
            CategorySelectFragment{ category ->
                updateCategory(category)
            }.show(supportFragmentManager, "select_category")
        }
    }

    private fun updateCategory(category: TransactionCategory) {
        selectedCategory = category
        binding.category.text = category.name
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
        if (selectedCategory == null) {
            Snackbar
                .make(binding.root, "Выберите категорию", Snackbar.LENGTH_LONG).show()
        } else {
            viewModel.trySendTransaction(
                MoneyTransaction(
                    null,
                    selectedCategory!!.isExpenses!!,
                    binding.etNameTrans.text.toString(),
                    binding.descriptionInput.text.toString(),
                    getTransactionDate(),
                    selectedCategory!!.id!!.toInt(),
                    selectedBillId!!.toInt(),
                    binding.etMoney.text.toString().toFloat()
                )
            )
        }
    }

    private fun getTransactionDate(): Long {
        return 0L
    }
}