package com.balancefy.balancefyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.components.GoalCard
import com.balancefy.balancefyapp.databinding.ResGoalCardBinding
import com.balancefy.balancefyapp.databinding.ResGoalRoadmapCardBinding
import com.balancefy.balancefyapp.models.response.GoalsResponse
import com.balancefy.balancefyapp.models.response.TaskResponse

class RoadmapCardsAdapter(
    private val taskList: List<TaskResponse>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = ResGoalRoadmapCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RoadmapCardHolder(inflater)
    }

    inner class RoadmapCardHolder(
        private val binding: ResGoalRoadmapCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun attach(taskCard : TaskResponse, lastTask: Boolean) {
            binding.title.text = taskCard.description
            binding.score.text = taskCard.score.toInt().toString()
            setColor(taskCard.done, binding.statusCard)

            if(lastTask) {
                binding.divider.visibility = View.GONE
            }
        }
    }

    fun setColor(done: Int, card: View) {
        if(done == 1) {
            card.setBackgroundColor(card.context.getColor(R.color.green_balancefy))
        } else {
            card.setBackgroundColor(card.context.getColor(R.color.lighter_grey_balancefy))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RoadmapCardHolder).attach(taskList[position], position == taskList.lastIndex)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}