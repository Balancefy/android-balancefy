package com.balancefy.balancefyapp.frames

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.balancefy.balancefyapp.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var chart: PieChart

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
        binding.nameUser.text = arguments?.getString("nameUser") ?: "Ze ninguem"

        initChart()

    }

    fun initChart() {

        var data = kotlin.collections.mutableListOf<PieEntry>()
        data.add(PieEntry(17f))
        data.add(PieEntry(50f))
        data.add(PieEntry(33f))

        var pieDataSet: PieDataSet = PieDataSet(data, "")
        pieDataSet.setDrawValues(false)
        pieDataSet.setColors(
            intArrayOf(
                Color.rgb(8, 248, 225),
                Color.rgb(62, 183, 67),
                Color.rgb(239, 51, 51)
            ) , 100)

        chart.data = PieData(pieDataSet)

        chart.animateXY(1000, 1000)
        chart.setHoleColor(Color.rgb(19 ,21 ,21));
        chart.setTransparentCircleColor(Color.argb(0, 0 ,0 ,0));
        chart.holeRadius = 85f
        chart.transparentCircleRadius = 0f

        chart.rotationAngle = -25f

        chart.isRotationEnabled = false
        chart.isHighlightPerTapEnabled = false

        chart.description.isEnabled = false
        chart.setEntryLabelColor(Color.argb(0, 19 ,21 ,21))

    }
}