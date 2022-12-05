package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName

data class FeedCommentReplyResponseDto (
    val id: Int,
    @SerializedName("descricao")
    val description: String,
    val createdAt: String,
    @SerializedName("fkConta")
    val autor: ContaResponseCommentDto
)

data class ContaResponseCommentDto(
    val id : Int,
    val renda : Double,
    val progresso: Double,
    val usuario: UserResponseDto
)