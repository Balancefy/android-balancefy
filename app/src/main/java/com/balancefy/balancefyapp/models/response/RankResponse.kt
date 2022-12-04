package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName

data class RankResponse(
    val rank: List<AccountResponse>
)

data class AccountResponse(
    val id: Int,
    @SerializedName("nome")
    val name: String,
    @SerializedName("progresso")
    val progress: Double,
    @SerializedName("objetivo")
    val goal: Int
)
