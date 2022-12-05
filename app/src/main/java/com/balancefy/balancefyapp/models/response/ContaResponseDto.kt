package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName

data class ContaResponseDto(
    val id : Int,
    val renda : Double,
    val progresso: Double,
    val status: Int,
    //@SerializedName("usuario")
    val fkUsuario: UserResponseDto
)