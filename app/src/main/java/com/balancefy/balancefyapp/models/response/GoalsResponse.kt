package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalDateTime

data class GoalsResponse (
    val id: Int,
    @SerializedName("categoria")
    val category: String,
    @SerializedName("descricao")
    val description: String,
    val done: Int,
    @SerializedName("valorTotal")
    val totalValue: Double,
    @SerializedName("valorInicial")
    val initialValue: Double,
    @SerializedName("tempoEstimado")
    val estimatedTime: LocalDate,
    @SerializedName("pontuacao")
    val score: Double,
    val createdAt: LocalDateTime
)