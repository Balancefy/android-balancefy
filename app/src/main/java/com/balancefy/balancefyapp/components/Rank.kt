package com.balancefy.balancefyapp.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ResRankBinding
import com.squareup.picasso.Picasso

@SuppressLint("SetTextI18n")
class Rank(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding: ResRankBinding =  ResRankBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.Rank)

        if(attributes.getBoolean(R.styleable.Rank_from_user, false)){
            binding.rankBackground.background.setTint(resources.getColor(R.color.green_balancefy))

        }
        binding.position.text = "${attributes.getString(R.styleable.Rank_position)}"
        binding.name.text = attributes.getString(R.styleable.Rank_name)
        binding.progress.text= attributes.getString(R.styleable.Rank_progress_value)
        binding.goalsCompleted.text = attributes.getString(R.styleable.Rank_goals_completed)


        if(!isInEditMode){
            Picasso.get().load(attributes.getString(R.styleable.Rank_image)).into(binding.image)
        }
    }

    fun setImg(avatar : String) {
        Picasso.get().load(avatar).into(binding.image)
    }

    fun setPosition(position : String){
        binding.position.text = position
    }

    fun setName(name : String) {
        binding.name.text = name
    }

    fun setProgress(progress: String){
        binding.progress.text = progress
    }

    fun setGoal(goal: String) {
        binding.goalsCompleted.text = goal
    }
}