package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("valor")
    val value: Double,
    @SerializedName("categoria")
    val category: String,
    @SerializedName("descricao")
    val description: String,
    @SerializedName("tipo")
    val type: String,
    val createdAt: String
)