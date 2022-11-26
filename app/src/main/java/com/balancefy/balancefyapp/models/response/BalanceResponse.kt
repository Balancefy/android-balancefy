package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName

data class BalanceResponse(
    @SerializedName("saldo")
    val saldo: Double,
    @SerializedName("entrada")
    val entrada: Double,
    @SerializedName("saida")
    val saida: Double,
)
