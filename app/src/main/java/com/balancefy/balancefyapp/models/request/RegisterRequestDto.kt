package com.balancefy.balancefyapp.models.request

import com.google.gson.annotations.SerializedName

data class RegisterRequestDto(
    @SerializedName("renda")
    val incoming: Double,
    @SerializedName("fkUsuario")
    val user: UserRegisterRequest
)
