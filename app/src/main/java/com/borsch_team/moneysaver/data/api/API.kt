package com.borsch_team.moneysaver.data.api

import android.util.Log
import com.borsch_team.moneysaver.data.databases.MoneySaverDatabase
import com.borsch_team.moneysaver.data.models.*
import com.google.firebase.auth.FirebaseAuth

class API(private val database: MoneySaverDatabase) {
    suspend fun getBills() =
        database.billDao().getAllBills()

    suspend fun getBill(billId: Long) =
        database.billDao().getBill(billId)

    suspend fun upsertBill(bill: Bill) =
        database.billDao().upsert(bill)

    suspend fun upsertCategory(category: TransactionCategory) {
        database.categoriesDao().upsert(category)
    }

    suspend fun upsertTransaction(transaction: MoneyTransaction) {
        database.transactionDao().upsert(transaction)
        payFromBill(transaction)
        if (transaction.isPlanned!!) {
            addReservedMoney(transaction)
        }
    }

    private suspend fun addReservedMoney(transaction: MoneyTransaction) {
        val bill = getBill(transaction.idBill!!.toLong())
        bill.reservedMoney = bill.reservedMoney?.plus(transaction.money!! * -1)
        upsertBill(bill)
    }

    private suspend fun payFromBill(transaction: MoneyTransaction) {
        val bill = getBill(transaction.idBill!!.toLong())
        bill.balance = bill.balance?.plus(transaction.money!!)
        upsertBill(bill)
    }

    suspend fun getExpensesCategory() =
        database.categoriesDao().getSpecificCategories(true)

    suspend fun getIncomesCategory() =
        database.categoriesDao().getSpecificCategories(false)

    suspend fun signInWithEmail(email: String, password: String, callback: (AuthResult) -> Unit){
        //signin in
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val user = FirebaseAuth.getInstance().currentUser
                callback(AuthResult(true, user!!, ""))
            }else{
                Log.d("FirebaseAuthSignIn", task.exception.toString())
                callback(AuthResult(false, null, task.exception.toString()))
            }
        }
    }

    suspend fun signUpWithEmail(email: String, password: String, callback: (AuthResult) -> Unit){
        //signin up
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val user = FirebaseAuth.getInstance().currentUser
                callback(AuthResult(true, user!!, ""))
            }else{
                Log.d("FirebaseAuthSignUp", task.exception.toString())
                callback(AuthResult(false, null, task.exception.toString()))
            }
        }
    }

    suspend fun resetPassword(email: String, callback: (AuthResult) -> Unit){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if(task.isSuccessful){
                callback(AuthResult(true, null, ""))
            }else{
                callback(AuthResult(false, null, task.exception.toString()))
            }
        }
    }

    suspend fun getExpensesTransactions(
        billID: Long,
        startTimestamp: Long,
        endTimestamp: Long
    ): List<TransactionAndCategory> =
        database.transactionDao().getExpensesTransactions(billID, startTimestamp, endTimestamp)

    suspend fun getIncomesTransactions(
        billID: Long,
        startTimestamp: Long,
        endTimestamp: Long
    ): List<TransactionAndCategory> =
        database.transactionDao().getIncomeTransactions(billID, startTimestamp, endTimestamp)

    suspend fun removeBill(billId: Long) {
        database.billDao().delete(billId)
        database.transactionDao().deleteBillTransactions(billId)
    }

    suspend fun getPlannedTransactions(): List<TransactionAndCategory> =
        database.transactionDao().getPlannedTransactions()
}