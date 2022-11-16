package com.balancefy.balancefyapp.models.request

import com.google.gson.annotations.SerializedName

data class RepeatedTransactionRequest(
    @SerializedName("valor")
    val value: Double,
    @SerializedName("categoria")
    val category: String,
    @SerializedName("descricao")
    val description: String,
    @SerializedName("tipo")
    val type: String
)