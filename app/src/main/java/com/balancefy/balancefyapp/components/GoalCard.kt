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
import com.balancefy.balancefyapp.databinding.ResTransactionBinding

class GoalCard(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding: ResGoalCardBinding = ResGoalCardBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.GoalCard)

        setTitle(attributes.getString(R.styleable.Transaction_category) ?: "")
        setCurrentBalance(attributes.getString(R.styleable.Transaction_category) ?: "")
        setAchievableBalance(attributes.getString(R.styleable.Transaction_category) ?: "")
        setEstimatedDate(attributes.getString(R.styleable.Transaction_category) ?: "")
    }

    fun setTitle(title: String) {
        binding.title.text = title
    }

    fun setCurrentBalance(balance: String) {
        binding.currentBalance.text = balance
    }

    fun setAchievableBalance(balance: String) {
        binding.achievableBalance.text = balance
    }

    fun setEstimatedDate(date: String) {
        binding.estimatedDate.text = date
    }
}