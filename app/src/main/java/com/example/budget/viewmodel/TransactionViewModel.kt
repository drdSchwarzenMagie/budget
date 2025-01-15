package com.example.budget.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.budget.data.AppDatabase
import com.example.budget.data.Transaction
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "budget_db"
    ).build()

    val transactions: LiveData<List<Transaction>> = db.transactionDao().getAllTransactions()

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            db.transactionDao().insertTransaction(transaction)
        }
        Log.d("TransactionViewModel", "Transactions: ${transactions.value}")
    }
}
