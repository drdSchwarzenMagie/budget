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
    private val db = AppDatabase.getDatabase(application)

    val transactions: LiveData<List<Transaction>> = db.transactionDao().getAllTransactions()

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            Log.d("TransactionViewModel", "Before insert: $transaction")
            db.transactionDao().insertTransaction(transaction)
            Log.d("TransactionViewModel", "After insert: $transaction")
        }
    }

}

