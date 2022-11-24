package com.balancefy.balancefyapp.models.request

import com.balancefy.balancefyapp.models.response.GoalsDetailsResponse
import com.balancefy.balancefyapp.models.response.GoalsResponse
import com.google.gson.annotations.SerializedName

data class GoalsDetailsRequest(
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
    val estimatedTime: String,
    @SerializedName("pontuacao")
    val score: Double,
    val createdAt: String
)