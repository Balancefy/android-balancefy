package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    val token: String,
    @SerializedName("conta")
    val account: AccountResponseDto?
)