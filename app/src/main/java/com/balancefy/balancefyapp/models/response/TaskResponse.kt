package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName

data class TaskResponse(
    val id: TaskGoalKey,
    @SerializedName("ordem")
    val order: Int,
    @SerializedName("descricao")
    val description: String,
    val done: Int,
    @SerializedName("pontuacao")
    val score: Double,
    @SerializedName("valor")
    val value: Double,
    val createdAt: String
)

data class TaskGoalKey(
    val taskId: Int,
    @SerializedName("objetivoContaId")
    val goalId: Int
)