package com.example.budget.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.ui.addtransaction.AddTransactionActivity
import com.example.budget.viewmodel.TransactionViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: TransactionViewModel
    private lateinit var adapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        adapter = TransactionAdapter()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.transactions.observe(this) { transactions ->
            Log.d("MainActivityLog", "Updated transactions: $transactions")
            adapter.updateTransactions(transactions)
        }

        findViewById<FloatingActionButton>(R.id.fab_add_transaction).setOnClickListener {
            startActivity(Intent(this, AddTransactionActivity::class.java))
        }
    }


}
