package com.balancefy.balancefyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balancefy.balancefyapp.R
import com.balancefy.balancefyapp.models.request.TopicoRequestDto
import com.balancefy.balancefyapp.models.response.GoalsResponse

class GoalCardsAdapter(
    private val goalList: List<GoalsResponse>,
    private val onClick: (mensagem: String) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val goalCardLayout = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.goal_card, parent, false)

        return GoalCardHolder(goalCardLayout)
    }

    inner class GoalCardHolder(
        private val goalCardLayout: View
    ) : RecyclerView.ViewHolder(goalCardLayout) {

        fun attach(goalCard : GoalsResponse) {
            val tvTitle = goalCardLayout.findViewById<TextView>(R.id.tv_goal_title)
            val tvAchievableBalance = goalCardLayout.findViewById<TextView>(R.id.tv_goal_achievable_balance)
            val tvActualBalance = goalCardLayout.findViewById<TextView>(R.id.tv_goal_actual_balance)
            val tvEstimatedDate = goalCardLayout.findViewById<TextView>(R.id.tv_goal_estimated_date)

            tvTitle.text = goalCard.description
            tvAchievableBalance.text = goalCard.totalValue.toString()
            tvActualBalance.text = goalCard.initialValue.toString()
            tvEstimatedDate.text = goalCard.estimatedTime.toString()

            goalCardLayout.setOnClickListener{
                onClick("Redirecionar para Post da Pessoa (in development)")
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