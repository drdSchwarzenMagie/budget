package com.example.budget.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT SUM(amount) FROM transactions WHERE type = :type")
    fun getTotalAmountByType(type: String): LiveData<Double?>


    @Query("SELECT * FROM transactions")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Insert
    suspend fun insertTransaction(transaction: Transaction): Long

    @Query("SELECT SUM(CASE WHEN type = 'income' THEN amount ELSE -amount END) FROM transactions")
    fun getTotalAmount(): LiveData<Double?>

}
