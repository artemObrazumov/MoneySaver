package com.borsch_team.moneysaver.ui.transaction_editor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import com.borsch_team.moneysaver.databinding.ActivityTransactionEditorBinding
import com.borsch_team.moneysaver.ui.qr_scanner.QrScannerActivity

@ExperimentalGetImage class TransactionEditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionEditorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransactionEditorBinding.inflate(layoutInflater)

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

        binding.tvScanQr.setOnClickListener {
            startActivity(Intent(this, QrScannerActivity::class.java))
        }

    }
}