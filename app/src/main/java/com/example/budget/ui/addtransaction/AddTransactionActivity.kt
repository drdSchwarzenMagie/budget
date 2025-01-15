package com.example.budget.ui.addtransaction

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.budget.R
import com.example.budget.data.Transaction
import com.example.budget.ui.main.TransactionAdapter
import com.example.budget.viewmodel.TransactionViewModel

class AddTransactionActivity : AppCompatActivity() {
    private lateinit var viewModel: TransactionViewModel
    private lateinit var adapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        adapter = TransactionAdapter()

        findViewById<Button>(R.id.button_save).setOnClickListener {
            val amount = findViewById<EditText>(R.id.edit_text_amount).text.toString().toDouble()
            val category = findViewById<EditText>(R.id.edit_text_category).text.toString()
            val type = if (findViewById<RadioButton>(R.id.radio_income).isChecked) "income" else "expense"
            val transaction = Transaction(amount = amount, category = category, type = type, date = System.currentTimeMillis())

            Log.d("TransactionViewModel", "Added transaction: $transaction")
            viewModel.addTransaction(transaction)
            finish()
        }
    }
}
