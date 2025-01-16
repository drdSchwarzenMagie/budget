package com.example.budget.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.budget.R
import com.example.budget.ui.addtransaction.AddTransactionActivity
import com.example.budget.viewmodel.TransactionViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: TransactionViewModel
    private lateinit var adapter: TransactionAdapter

    private val addTransactionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        // Повторно обновляем данные после возврата
        viewModel.transactions.observe(this) { transactions ->
            adapter.updateTransactions(transactions)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        adapter = TransactionAdapter()

        // Настройка RecyclerView для списка транзакций
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Наблюдение за изменениями в транзакциях
        viewModel.transactions.observe(this) { transactions ->
            adapter.updateTransactions(transactions)
        }

        // Наблюдение за общей суммой
        val totalAmountTextView = findViewById<TextView>(R.id.text_total_amount)
        viewModel.totalAmount.observe(this) { totalAmount ->
            totalAmountTextView.text = "Total: ${totalAmount ?: 0.00}"
        }

        // Обработчик нажатия на кнопку добавления транзакции
        findViewById<FloatingActionButton>(R.id.fab_add_transaction).setOnClickListener {
            addTransactionLauncher.launch(Intent(this, AddTransactionActivity::class.java))
        }
    }
}
