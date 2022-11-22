package com.balancefy.balancefyapp.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ResGoalRoadmapCardBinding

class RoadmapCard(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding: ResGoalRoadmapCardBinding = ResGoalRoadmapCardBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.GoalRoadmapCard)

        setTitle(attributes.getString(R.styleable.GoalRoadmapCard_roadmap_title) ?: "")
        setScore(attributes.getString(R.styleable.GoalRoadmapCard_roadmap_score) ?: "")
        setStatus(attributes.getString(R.styleable.GoalRoadmapCard_roadmap_status) ?: "")
    }

    fun setTitle(title: String) {
        binding.title.text = title
    }

    fun setScore(score: String) {
        binding.score.text = score
    }

    fun setStatus(done: String) {
        if(done.isNotEmpty()) {
            binding.statusCard.setBackgroundColor(context.getColor(R.color.green_balancefy))
        } else {
            binding.statusCard.setBackgroundColor(context.getColor(R.color.lighter_grey_balancefy))
        }
    }
}