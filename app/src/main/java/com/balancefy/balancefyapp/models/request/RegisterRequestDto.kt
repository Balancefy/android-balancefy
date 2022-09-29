package com.balancefy.balancefyapp.models.request

import com.google.gson.annotations.SerializedName

data class RegisterRequestDto(
    val nome: String,
    val email: String,
    @SerializedName("senha")
    val password: String,

)
