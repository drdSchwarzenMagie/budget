package com.example.budget.ui.chart

import android.os.Bundle
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.budget.R
import com.example.budget.viewmodel.TransactionViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlin.random.Random

class ChartActivity : AppCompatActivity() {
    private lateinit var viewModel: TransactionViewModel
    private lateinit var incomePieChart: PieChart
    private lateinit var expensePieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        incomePieChart = findViewById(R.id.incomePieChart)
        expensePieChart = findViewById(R.id.expensePieChart)

        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        viewModel.transactions.observe(this) { transactions ->
            // Группировка данных по категориям для доходов
            val incomeCategories = transactions.filter { it.type == "income" }
                .groupBy { it.category }
                .map { it.key to it.value.sumOf { transaction -> transaction.amount } }
                .sortedByDescending { it.second }

            // Группировка данных по категориям для расходов
            val expenseCategories = transactions.filter { it.type == "expense" }
                .groupBy { it.category }
                .map { it.key to it.value.sumOf { transaction -> transaction.amount } }
                .sortedByDescending { it.second }

            // Построим график для доходов
            val incomeEntries = incomeCategories.map { PieEntry(it.second.toFloat(), it.first) }
            val incomeDataSet = PieDataSet(incomeEntries, "Income Categories")
            incomeDataSet.colors = generateRandomColors(incomeEntries.size)
            incomePieChart.data = PieData(incomeDataSet)
            incomePieChart.invalidate()

            // Построим график для расходов
            val expenseEntries = expenseCategories.map { PieEntry(it.second.toFloat(), it.first) }
            val expenseDataSet = PieDataSet(expenseEntries, "Expense Categories")
            expenseDataSet.colors = generateRandomColors(expenseEntries.size)
            expensePieChart.data = PieData(expenseDataSet)
            expensePieChart.invalidate()
        }
    }

    // Генерация случайных цветов
    private fun generateRandomColors(size: Int): List<Int> {
        val random = Random
        return List(size) {
            Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
        }
    }
}
