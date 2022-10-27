package com.balancefy.balancefyapp.models.response

import java.time.LocalDateTime

data class TopicoResponseDto(
    val id : Int,
    val titulo: String,
    val descricao: String,
    val likes: Int,
    val createdAt: LocalDateTime,
    val fkConta : ContaResponseDto
)