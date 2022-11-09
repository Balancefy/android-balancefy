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
import com.balancefy.balancefyapp.databinding.ResGoalDetailsBinding
import com.balancefy.balancefyapp.databinding.ResTransactionBinding

class GoalDetails(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding: ResGoalDetailsBinding = ResGoalDetailsBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.GoalDetails)

        setTitle(attributes.getString(R.styleable.GoalDetails_goal_details_title) ?: "")
        setRemainingDays(attributes.getString(R.styleable.GoalDetails_remaining_days) ?: "")
    }

    fun setTitle(title: String) {
        binding.title.text = title
    }

    fun setRemainingDays(days: String) {
        binding.remainingDays.text = days
    }
}