package com.example.budget.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budget.R
import com.example.budget.data.Transaction

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private val transactions = mutableListOf<Transaction>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateTransactions(newTransactions: List<Transaction>) {
        transactions.clear()
        transactions.addAll(newTransactions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int = transactions.size

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(transaction: Transaction) {
            val categoryTextView = itemView.findViewById<TextView>(R.id.categoryTextView)
            val amountTextView = itemView.findViewById<TextView>(R.id.amountTextView)

            categoryTextView.text = transaction.category
            val formattedAmount = if (transaction.type == "income") {
                "+${transaction.amount}"
            } else {
                "-${transaction.amount}"
            }
            amountTextView.text = formattedAmount

            val color = if (transaction.type == "income") {
                itemView.context.getColor(android.R.color.holo_green_dark)
            } else {
                itemView.context.getColor(android.R.color.holo_red_dark)
            }
            amountTextView.setTextColor(color)
        }
    }
}

