package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName

data class AccountResponseDto(
    val id: Int,
    @SerializedName("renda")
    val incoming: Double,
    @SerializedName("progresso")
    val progress: Double,
    @SerializedName("usuario")
    val user: UserResponseDto
)