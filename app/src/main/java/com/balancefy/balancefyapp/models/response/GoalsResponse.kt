package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalDateTime

data class GoalsResponse (
    val id: Int,
    @SerializedName("conta")
    val account: AccountResponseDto,
    @SerializedName("objetivo")
    val category: Goal,
    @SerializedName("descricao")
    val description: String,
    val done: Int,
    @SerializedName("valorTotal")
    val totalValue: Double,
    @SerializedName("valorInicial")
    val initialValue: Double,
    @SerializedName("tempoEstimado")
    val estimatedTime: String,
    @SerializedName("pontuacao")
    val score: Double,
    val createdAt: String
)

data class Goal(
    val id: Int,
    @SerializedName("categoria")
    val category: String
)