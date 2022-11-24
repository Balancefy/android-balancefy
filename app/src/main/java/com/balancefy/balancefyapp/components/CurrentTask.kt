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
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ResCurrentTaskBinding

class CurrentTask(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding: ResCurrentTaskBinding = ResCurrentTaskBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CurrentTask)

        setTitle(attributes.getString(R.styleable.CurrentTask_current_task_title) ?: "")
        setDescription(attributes.getString(R.styleable.CurrentTask_current_task_description) ?: "")
        setScore(attributes.getString(R.styleable.CurrentTask_score) ?: "")
        setNextStepOnClickListener()
    }

    fun setNextStepOnClickListener() {
        binding.btnNextStep.setOnClickListener {
            findNavController().navigate(R.id.fromGoalDetailsToGoalRoadmap)
        }
    }

    fun setCompleteOnClickListener(function: () -> Unit) {
        binding.btnDone.setOnClickListener {
            function()
        }
    }

    fun setTitle(title: String) {
        binding.taskTitle.text = title
    }

    fun setDescription(balance: String) {
        binding.taskDescription.text = balance
    }

    fun setScore(balance: String) {
        binding.taskScore.text = balance
    }
}