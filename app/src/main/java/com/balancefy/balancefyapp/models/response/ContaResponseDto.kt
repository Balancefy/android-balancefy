package com.balancefy.balancefyapp.models.response

data class ContaResponseDto(
    val id : Int,
    val renda : Double,
    val progresso: Double,
    val status: Int,
    val fkUsuario: UserResponseDto
)