package com.balancefy.balancefyapp.components

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ResGoalCardBinding
import com.balancefy.balancefyapp.databinding.ResGoalProgressBinding
import com.balancefy.balancefyapp.databinding.ResTransactionBinding

class GoalProgress(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding: ResGoalProgressBinding = ResGoalProgressBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.GoalProgress)

        setTotalScore(attributes.getString(R.styleable.GoalProgress_total_score) ?: "")
    }

    fun setTotalScore(score: String) {
        binding.totalScore.text = score
    }

    fun setProgress(progress: Double) {
        binding.progressIndicator.progress = progress.toInt()
    }
}