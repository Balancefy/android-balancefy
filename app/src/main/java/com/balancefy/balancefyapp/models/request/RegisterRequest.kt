package com.balancefy.balancefyapp.models.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("renda")
    val incoming: Double,
    @SerializedName("fkUsuario")
    val user: UserRegisterRequest
)
