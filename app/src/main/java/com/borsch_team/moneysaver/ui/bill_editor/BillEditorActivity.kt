package com.borsch_team.moneysaver.ui.bill_editor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.borsch_team.moneysaver.Constants
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.data.models.Bill
import com.borsch_team.moneysaver.databinding.ActivityBillEditorBinding
import com.borsch_team.moneysaver.ui.adapter.BillTypeAdapter
import com.borsch_team.moneysaver.ui.dialog.CompletedDialog

class BillEditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBillEditorBinding
    private lateinit var viewModel: BillEditorViewModel
    private var billId: Long? = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillEditorBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[BillEditorViewModel::class.java]
        viewModel.bill.observe(this) { bill ->
            binding.titleInput.setText(bill.name)
            binding.typeSelect.setSelection(bill.idType!!)
            binding.balanceInput.setText(bill.balance.toString())
        }
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Добавление счёта"
        checkIfEditing()
        initializeSpinner()
    }

    private fun initializeSpinner() {
        val spinnerAdapter = BillTypeAdapter(this, Constants.BILL_TYPES)
        binding.typeSelect.adapter = spinnerAdapter
    }

    private fun checkIfEditing() {
        billId = intent.getLongExtra("billId", -1L)
        if (billId != -1L) {
            supportActionBar?.title = "Редактирование счёта"
            viewModel.getBill(billId!!)
            binding.balanceInput.isEnabled = false
            binding.remove.visibility = View.VISIBLE
        }
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
        if (billId == -1L) {
            billId = null
        }
        val bill = Bill(
            billId,
            binding.typeSelect.selectedItemPosition,
            binding.titleInput.text.toString().trim(),
            binding.balanceInput.text.toString().trim().toFloat(),
        )
        viewModel.uploadBill(bill)
        CompletedDialog("Счёт загружен"){
            onBackPressed()
        }
    }
}