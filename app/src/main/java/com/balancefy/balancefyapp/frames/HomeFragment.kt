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
import com.balancefy.balancefyapp.adapter.TransactionCardsAdapter
import com.balancefy.balancefyapp.databinding.FragmentHomeBinding
import com.balancefy.balancefyapp.models.response.*
import com.balancefy.balancefyapp.rest.Rest
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Float
import java.util.*
import kotlin.String
import kotlin.Throwable
import kotlin.intArrayOf
import kotlin.toString

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var chart: PieChart

    private lateinit var preferences : SharedPreferences
    private var token: String? = null

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

        binding.nameUser.text = arguments?.getString("nameUser") ?: "Ze ninguem"

        initChart()
        getBalances()
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
        chart.rotationAngle = -25f
        chart.isRotationEnabled = false
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

                        binding.saldoAtual.text = data?.saldo.toString()
                        binding.receitaAtual.text = data?.entrada.toString()
                        binding.despesaAtual.text = data?.saida.toString()

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

                    }
                }
            }

            override fun onFailure(call: Call<BalanceResponse>, t: Throwable) {
                println("balance error: "+context?.getString(R.string.connection_error))
            }
        })
    }

    private fun getTransactionsFixed() {

        val accountId : Int = arguments?.getString("accountId")!!.toInt()

        Rest.getRepeatedTransactionInstance().getList("Bearer $token", accountId).enqueue(object : Callback<List<RepeatedTransactionResponse>> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<List<RepeatedTransactionResponse>>,
                response: Response<List<RepeatedTransactionResponse>>
            ) {
                when(response.code()){
                    200 -> {
                        configRecyclerView(response.body()!!)
                    }
                    else -> {
                        Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<RepeatedTransactionResponse>>, t: Throwable) {
                Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun configRecyclerView(transaction: List<RepeatedTransactionResponse>) {
        val recyclerContainer = binding.recyclerContainer
        recyclerContainer.layoutManager = LinearLayoutManager(context)

        recyclerContainer.adapter = TransactionCardsAdapter(
            transaction,
            requireContext()
        )
    }

}