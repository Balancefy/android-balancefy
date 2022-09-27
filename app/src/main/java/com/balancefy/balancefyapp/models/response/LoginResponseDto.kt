package com.balancefy.balancefyapp.models.response

data class LoginResponseDto(
    val token: String,
    val conta: ContaResponseDto?
)