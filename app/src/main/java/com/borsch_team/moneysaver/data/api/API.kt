package com.borsch_team.moneysaver.data.api

import android.util.Log
import com.borsch_team.moneysaver.App
import com.borsch_team.moneysaver.data.databases.MoneySaverDatabase
import com.borsch_team.moneysaver.data.models.*
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar

class API(private val database: MoneySaverDatabase) {
    suspend fun getBills() =
        database.billDao().getAllBills()

    suspend fun getBill(billId: Long) =
        database.billDao().getBill(billId)

    suspend fun upsertBill(bill: Bill) =
        database.billDao().upsert(bill).also {
            App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
        }

    suspend fun upsertCategory(category: TransactionCategory) {
        database.categoriesDao().upsert(category).also {
            App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
        }
    }

    suspend fun upsertTransaction(transaction: MoneyTransaction) {
        database.transactionDao().upsert(transaction)
        payFromBill(transaction)
        if (transaction.isPlanned!! && transaction.isExpenses!!) {
            addReservedMoney(transaction)
        }
        App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
    }

    private suspend fun upsertTransactionRaw(transaction: MoneyTransaction) {
        database.transactionDao().upsert(transaction).also {
            App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
        }
    }

    private suspend fun addReservedMoney(transaction: MoneyTransaction) {
        val bill = getBill(transaction.idBill!!.toLong())
        bill.reservedMoney = bill.reservedMoney?.plus(transaction.money!! * -1)
        upsertBill(bill).also {
            App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
        }
    }

    private suspend fun payFromBill(transaction: MoneyTransaction) {
        if (transaction.isPlanned!! && !transaction.isExpenses!!) return
        val bill = getBill(transaction.idBill!!.toLong())
        bill.balance = bill.balance?.plus(transaction.money!!)
        upsertBill(bill).also {
            App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
        }
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
        database.transactionDao().deleteBillTransactions(billId).also {
            App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
        }
    }

    suspend fun getPlannedTransactions(): List<TransactionAndCategory> =
        database.transactionDao().getPlannedTransactions()

    suspend fun removeTransaction(transaction: MoneyTransaction) {
        database.transactionDao().delete(transaction.id!!)
        cancelTransaction(transaction).also {
            App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
        }
    }

    private suspend fun cancelTransaction(transaction: MoneyTransaction) {
        putBackToBill(transaction)
        if (transaction.isPlanned!!) {
            decreaseReserved(transaction)
        }
        App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
    }

    private suspend fun decreaseReserved(transaction: MoneyTransaction) {
        val bill = getBill(transaction.idBill!!.toLong())
        if (transaction.isExpenses!!) {
            bill.reservedMoney = bill.reservedMoney?.plus(transaction.money!!)
        }
        upsertBill(bill)
        App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
    }

    private suspend fun putBackToBill(transaction: MoneyTransaction) {
        if (transaction.isPlanned!! && !transaction.isExpenses!!) return
        val bill = getBill(transaction.idBill!!.toLong())
        bill.balance = bill.balance?.minus(transaction.money!!)
        upsertBill(bill).also {
            App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
        }
    }

    suspend fun getAllIncomesTransactions(
        startTimestamp: Long,
        endTimestamp: Long
    ): List<TransactionAndCategory> =
        database.transactionDao().getAllIncomeTransactions(startTimestamp, endTimestamp)

    suspend fun getAllExpensesTransactions(
        startTimestamp: Long,
        endTimestamp: Long
    ): List<TransactionAndCategory> =
        database.transactionDao().getAllExpensesTransactions(startTimestamp, endTimestamp)

    suspend fun performPlannedTransaction(transaction: MoneyTransaction) {
        if (transaction.isExpenses!!) {
            decreaseReservedMoney(transaction)
        } else {
            addToBill(transaction)
        }
        database.transactionDao().delete(transaction.id!!)
        changeTransactionPlannedType(transaction).also {
            App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
        }
    }

    private suspend fun addToBill(transaction: MoneyTransaction) {
        val bill = getBill(transaction.idBill!!.toLong())
        bill.balance = bill.balance?.plus(kotlin.math.abs(transaction.money!!))
        upsertBill(bill).also {
            App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
        }
    }

    private suspend fun changeTransactionPlannedType(transaction: MoneyTransaction) {
        transaction.isPlanned = false
        upsertTransactionRaw(transaction).also {
            App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
        }
    }

    private suspend fun getTransaction(transactionId: Long): MoneyTransaction =
        database.transactionDao().getTransaction(transactionId)

    private suspend fun decreaseReservedMoney(transaction: MoneyTransaction) {
        val bill = getBill(transaction.idBill!!.toLong())
        bill.reservedMoney = bill.reservedMoney?.minus(kotlin.math.abs(transaction.money!!))
        upsertBill(bill).also {
            App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
        }
    }

    suspend fun getAllCategories() =
        database.categoriesDao().getAllCategories()

    suspend fun getAllTransactions() =
        database.transactionDao().getAllTransactionsNoCategory()

    suspend fun getAllBills() =
        database.billDao().getAllBills()

    suspend fun getCustomCategories() =
        database.categoriesDao().getCustomCategories()

    suspend fun formUserSavefile(): UserSavefile {
        val username = App.preferencesManager.getUsername()!!
        val categories = getCustomCategories()
        val transactions = getAllTransactions()
        val bills = getAllBills()

        return UserSavefile(
            username,
            bills,
            categories,
            transactions
        ).also {
            App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
        }
    }

    suspend fun reloadUserData(loadUserData: UserSavefile) {
        App.preferencesManager.saveUsername(loadUserData.name)
        clearDatabase()
        loadUserData.bills.forEach {
            upsertBill(it)
        }
        loadUserData.transactions.forEach {
            upsertTransactionRaw(it)
        }
        loadUserData.categories.forEach {
            upsertCategory(it)
        }
        App.preferencesManager.saveLastTimeUpdate(Calendar.getInstance().timeInMillis)
    }

    private suspend fun clearDatabase() {
        clearCustomCategories()
        clearTransactions()
        clearBills()
    }

    private suspend fun clearCustomCategories() {
        database.categoriesDao().clearCustomCategories()
    }

    private suspend fun clearTransactions() {
        database.transactionDao().clearTransactions()
    }

    private suspend fun clearBills() {
        database.billDao().clearBills()
    }
}