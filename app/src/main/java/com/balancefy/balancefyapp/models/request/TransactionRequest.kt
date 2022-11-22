package com.balancefy.balancefyapp.models.request

import com.balancefy.balancefyapp.models.response.GoalsResponse
import com.google.gson.annotations.SerializedName

data class TransactionRequest(
    @SerializedName("valor")
    val value: Double,
    @SerializedName("categoria")
    val category: String,
    @SerializedName("descricao")
    val description: String,
    @SerializedName("tipo")
    val type: String,
    @SerializedName("fkObjetivoConta")
    val goal: GoalsResponse
)