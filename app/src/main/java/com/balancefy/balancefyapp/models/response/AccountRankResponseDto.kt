package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName

data class AccountRankResponseDto(
    val id: Int,
    @SerializedName("nome")
    val name: String,
    @SerializedName("progresso")
    val progress: Double,
    @SerializedName("objetivo")
    val goal: Int,
    var avatar: String = ""
)