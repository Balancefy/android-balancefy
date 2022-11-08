package com.balancefy.balancefyapp.models.response

data class TopicoResponseDto(
    val message: String,
    val id : Int,
    val titulo: String,
    val descricao: String,
    val likes: Int,
    val createdAt: String
)