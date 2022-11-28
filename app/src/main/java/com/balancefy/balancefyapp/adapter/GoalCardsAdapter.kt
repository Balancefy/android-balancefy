package com.balancefy.balancefyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.balancefy.balancefyapp.databinding.ResGoalCardBinding
import com.balancefy.balancefyapp.models.response.GoalsResponse
import java.time.LocalDate.*
import java.time.format.DateTimeFormatter

class GoalCardsAdapter(
    private val goalList: List<GoalsResponse>,
    private val onClick: (id: Int) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = ResGoalCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GoalCardHolder(inflater)
    }

    inner class GoalCardHolder(
        private val binding: ResGoalCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun attach(goalCard : GoalsResponse) {
            val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM',' yyyy")
            val date = parse(goalCard.estimatedTime.replace("-", ""), DateTimeFormatter.BASIC_ISO_DATE)

            binding.title.text = goalCard.description
            binding.achievableBalance.text = goalCard.totalValue.toString()
            binding.currentBalance.text = goalCard.initialValue.toString()
            binding.estimatedDate.text = date.format(formatter)

            binding.card.setOnClickListener {
                onClick(goalCard.id)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GoalCardHolder).attach(goalList[position])
    }

    override fun getItemCount(): Int {
        return goalList.size
    }
}