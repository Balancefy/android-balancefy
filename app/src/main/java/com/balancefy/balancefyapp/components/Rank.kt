package com.balancefy.balancefyapp.components

import android.content.Context
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.databinding.ResRankBinding
import com.balancefy.balancefyapp.databinding.ResTipComponentBinding
import java.net.URL

class Rank(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val binding: ResRankBinding =  ResRankBinding.inflate(
        LayoutInflater.from(context), this, true
    )
    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.Rank)

        if(attributes.getBoolean(R.styleable.Rank_from_user, false)){
            binding.rankBackground.background.setTint(resources.getColor(R.color.green_balancefy))

        }
        binding.goalsCompleted.text = attributes.getString(R.styleable.Rank_goals_completed)
        binding.tasksCompleted.text= attributes.getString(R.styleable.Rank_tasks_completed)
        binding.name.text = attributes.getString(R.styleable.Rank_name)
        binding.position.text = "${attributes.getString(R.styleable.Rank_position)} º"

        val imageUrl = URL(attributes.getString(R.styleable.Rank_image))
        val photo = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
        binding.image.setImageBitmap(photo)

    }


}