package com.borsch_team.moneysaver.ui.transaction_editor

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.Constants
import com.borsch_team.moneysaver.R
import com.borsch_team.moneysaver.data.models.MoneyTransaction
import com.borsch_team.moneysaver.data.models.TransactionCategory
import com.borsch_team.moneysaver.databinding.ActivityTransactionEditorBinding
import com.borsch_team.moneysaver.setupTheme
import com.borsch_team.moneysaver.ui.adapter.BillsAdapter
import com.borsch_team.moneysaver.ui.category_select.CategorySelectFragment
import com.borsch_team.moneysaver.ui.dialog.CompletedDialog
import com.borsch_team.moneysaver.ui.qr_scanner.QrScannerActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

@ExperimentalGetImage class TransactionEditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionEditorBinding
    private lateinit var viewModel: TransactionEditorViewModel
    private var selectedCategory: TransactionCategory? = null
    private var selectedBillId: Long? = null
    private lateinit var billsAdapter: BillsAdapter
    private var transactionTime: Long? = null
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        setupTheme()
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
        billsAdapter = BillsAdapter(emptyList(), {}, {}, false)
        viewModel.bills.observe(this) { bills ->
            billsAdapter.updateDataset(bills)
        }
        viewModel.transactionSendResult.observe(this) { result ->
            when (result) {
                Constants.TRANSACTION_RESULT_MONEY_ERROR -> {
                    Snackbar
                        .make(binding.root, "Недостаточно средств для совершения операции", Snackbar.LENGTH_LONG).show()
                }
                else -> {
                    CompletedDialog("Операция загружена"){
                        onBackPressed()
                    }.show(supportFragmentManager, "completed")
                }
            }
        }
        viewModel.getBills()
        binding.billsPager.adapter = billsAdapter
        binding.billsPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                try {
                    selectedBillId = billsAdapter.getBill(position).id
                } catch (_: java.lang.Exception) {}
            }
        })
        TabLayoutMediator(binding.tabDots, binding.billsPager, true) { _, _ -> }.attach()
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
        binding.selectTime.setOnClickListener {
            selectTime()
        }
        selectCurrentTime()
    }

    private fun selectCurrentTime() {
        onTimeSelected(Calendar.getInstance().timeInMillis)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun selectTime() {
        val cal = android.icu.util.Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(android.icu.util.Calendar.YEAR, year)
            cal.set(android.icu.util.Calendar.MONTH, monthOfYear)
            cal.set(android.icu.util.Calendar.DAY_OF_MONTH, dayOfMonth)
            cal.set(java.util.Calendar.HOUR_OF_DAY, 0)
            cal.set(java.util.Calendar.MINUTE, 0)
            cal.set(java.util.Calendar.SECOND, 0)
            cal.set(java.util.Calendar.MILLISECOND, 0)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = android.icu.text.SimpleDateFormat(myFormat, Locale.US)
            //binding.tvDateOt.text = sdf.format(cal.time)
            onTimeSelected(cal.time.time)
        }

        DatePickerDialog(this, dateSetListener,
            cal.get(android.icu.util.Calendar.YEAR),
            cal.get(android.icu.util.Calendar.MONTH),
            cal.get(android.icu.util.Calendar.DAY_OF_MONTH)).show()
    }

    private fun onTimeSelected(time: Long) {
        binding.time.text =
            SimpleDateFormat(Constants.TIME_FORMAT_PATTERN, Locale("ru")).format(time)
        transactionTime = time
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
        } else if(binding.etNameTrans.text.toString() == ""){
            Snackbar
                .make(binding.root, "Введите транзакцию", Snackbar.LENGTH_LONG).show()
        } else if(!binding.etMoney.text.toString().contains("[0-9]".toRegex())) {
            Snackbar
                .make(binding.root, "Введите корректную стоимость", Snackbar.LENGTH_LONG).show()
        } else {
            viewModel.bills.observe(this){
                if(it.isEmpty()){
                    Snackbar
                        .make(binding.root, "Отсутствует счёт", Snackbar.LENGTH_LONG).show()
                }else{
                    viewModel.trySendTransaction(
                        MoneyTransaction(
                            null,
                            selectedCategory!!.isExpenses!!,
                            binding.etNameTrans.text.toString(),
                            "",
                            getTransactionDate(),
                            selectedCategory!!.id!!.toInt(),
                            selectedBillId!!.toInt(),
                            binding.etMoney.text.toString().toFloat() * moneyKoeff(),
                            binding.isPlanned.isChecked
                        )
                    )
                }
            }
        }
    }

    private fun moneyKoeff(): Int =
        if (selectedCategory!!.isExpenses!!) { -1 }
        else { 1 }

    private fun getTransactionDate(): Long = transactionTime!!
}