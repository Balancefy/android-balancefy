package com.balancefy.balancefyapp.frames

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.adapter.TipCardAdapter
import com.balancefy.balancefyapp.adapter.TransactionCardsAdapter
import com.balancefy.balancefyapp.databinding.FragmentHomeBinding
import com.balancefy.balancefyapp.models.response.BalanceResponse
import com.balancefy.balancefyapp.models.response.TipsResponse
import com.balancefy.balancefyapp.models.response.TransactionResponse
import com.balancefy.balancefyapp.rest.Rest
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Float
import kotlin.Int
import kotlin.String
import kotlin.Throwable
import kotlin.intArrayOf
import kotlin.toString

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    lateinit var preferences : SharedPreferences
    private lateinit var chart: PieChart

    private var token: String? = null
    private var accountId : Int? = null
    private var filterType: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        chart = binding.chartMassa

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        preferences = context?.getSharedPreferences("Auth", AppCompatActivity.MODE_PRIVATE)!!

        token = preferences.getString("token", null)
        accountId = arguments?.getInt("accountId")!!

        binding.nameUser.text = arguments?.getString("nameUser") ?: "Ze ninguem"

        listenScroll()
        initChart()
        getBalances()
        getTransactionsFixed()
        getTips()

    }

    fun listenScroll() {
        binding.scrollFilterChart.setOnScrollChangeListener { view, i, atual, i3, antigo ->
            val scrollValue = Float.parseFloat(1.5.toString())

            if(atual < antigo) {
                chart.rotationAngle = chart.rotationAngle + scrollValue
                this.controlFilterTransactions(atual)
                chart.invalidate()
            } else {
                chart.rotationAngle = chart.rotationAngle - scrollValue
                this.controlFilterTransactions(atual)
                chart.invalidate()
            }
        }
    }

    fun controlFilterTransactions(atual: Int) {
        when(atual){
            in 320..450 -> if (filterType != "Entrada") filterTransactions("Entrada")
            in 451..690 -> if (filterType != "Saida") filterTransactions("Saida")
            else -> filterTransactions("")
        }
    }

    fun filterTransactions(typeTransaction: String) {
        filterType = typeTransaction;
        getTransactionsFixed()
    }

    fun initChart() {

        chart.animateXY(1000, 1000)
        chart.setHoleColor(Color.rgb(19 ,21 ,21));
        chart.setTransparentCircleColor(Color.argb(0, 0 ,0 ,0));
        chart.setEntryLabelColor(Color.argb(0, 19 ,21 ,21))
        chart.description.isEnabled = false
        chart.holeRadius = 85f
        chart.transparentCircleRadius = 0f
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = false

    }

    private fun getBalances() {

        Rest.getBalanceInstance().getBalance("Bearer ${token!!}").enqueue(object :
            Callback<BalanceResponse> {
            override fun onResponse(
                call: Call<BalanceResponse>,
                response: Response<BalanceResponse>
            ) {
                val data = response.body()
                when(response.code()){
                    200 -> {

                        binding.saldoAtual.text = String.format("%.2f", data?.saldo)
                        binding.receitaAtual.text = String.format("%.2f", data?.entrada)
                        binding.despesaAtual.text = String.format("%.2f", data?.saida)

                        var dataChart = kotlin.collections.mutableListOf<PieEntry>()
                        dataChart.add(PieEntry(Float.parseFloat(data?.saldo.toString())))
                        dataChart.add(PieEntry(Float.parseFloat(data?.entrada.toString())))
                        dataChart.add(PieEntry(Float.parseFloat(data?.saida.toString())))

                        var pieDataSet: PieDataSet = PieDataSet(dataChart, "")
                        pieDataSet.setDrawValues(false)
                        pieDataSet.setColors(
                            intArrayOf(
                                Color.rgb(8, 248, 225),
                                Color.rgb(62, 183, 67),
                                Color.rgb(239, 51, 51)
                            ),
                            100
                        )

                        chart.data = PieData(pieDataSet)
                        chart.rotationAngle = -85f

                    }
                }
            }

            override fun onFailure(call: Call<BalanceResponse>, t: Throwable) {
                println("balance error: "+context?.getString(R.string.connection_error))

                val zero = 0.0

                binding.saldoAtual.text = String.format("%.2f", zero)
                binding.receitaAtual.text = String.format("%.2f", zero)
                binding.despesaAtual.text = String.format("%.2f", zero)

                var dataChart = kotlin.collections.mutableListOf<PieEntry>()
                dataChart.add(PieEntry(Float.parseFloat(zero.toString())))
                dataChart.add(PieEntry(Float.parseFloat(zero.toString())))
                dataChart.add(PieEntry(Float.parseFloat(zero.toString())))

                var pieDataSet: PieDataSet = PieDataSet(dataChart, "")
                pieDataSet.setDrawValues(false)
                pieDataSet.setColors(
                    intArrayOf(
                        Color.rgb(8, 248, 225),
                        Color.rgb(62, 183, 67),
                        Color.rgb(239, 51, 51)
                    ),
                    100
                )

                chart.data = PieData(pieDataSet)
                chart.rotationAngle = 0f
            }
        })
    }

    private fun getTransactionsFixed() {

        Rest.getRepeatedTransactionInstance().getList("Bearer $token", accountId!!).enqueue(object : Callback<List<TransactionResponse>> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<List<TransactionResponse>>,
                response: Response<List<TransactionResponse>>
            ) {
                when(response.code()){
                    200 -> {
                        configRecyclerViewTransactions(response.body() ?: emptyList(), filterType)
                    }
                    else -> {
                        Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<TransactionResponse>>, t: Throwable) {
                Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun configRecyclerViewTransactions(transactions: List<TransactionResponse>, type: String?) {
        if(transactions.isEmpty()) {
            binding.tvError.visibility = View.VISIBLE
        } else {
            binding.tvError.visibility = View.GONE
            binding.recyclerContainer.visibility = View.VISIBLE

            val recyclerContainer = binding.recyclerContainer
            recyclerContainer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            if (!type.isNullOrBlank()) {
                recyclerContainer.adapter = TransactionCardsAdapter(
                    transactions.filter{ it.type == type },
                    requireContext()
                )
            } else {
                recyclerContainer.adapter = TransactionCardsAdapter(transactions, requireContext())
            }
        }
    }

    private fun getTips() {

        Rest.getTipsInstance().getAllTips("Bearer $token").enqueue(object: Callback<List<TipsResponse>>{
            override fun onResponse(
                call: Call<List<TipsResponse>>,
                response: Response<List<TipsResponse>>
            ) {
                when(response.code()) {
                    200 -> {
                        configRecyclerViewTips(response.body() ?: emptyList())
                    }
                    else -> {
                        Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<TipsResponse>>, t: Throwable) {
                Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show()
            }

        } )
    }

    fun configRecyclerViewTips(list: List<TipsResponse>) {
        val recyclerContainer = binding.recyclerContainerTips
        recyclerContainer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerContainer.adapter = TipCardAdapter(list)
    }
}