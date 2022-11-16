package com.balancefy.balancefyapp.models.request

import com.google.gson.annotations.SerializedName

data class CreateGoal(
    @SerializedName("objetivo")
    val goal: GoalCategory,
    @SerializedName("descricao")
    val description: String,
    @SerializedName("valorTotal")
    val totalValue: Double,
    @SerializedName("valorInicial")
    val initialValue: Double,
    @SerializedName("tempoEstimado")
    val estimatedTime: String
)

data class GoalCategory(
    val id: Int
)
