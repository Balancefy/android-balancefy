package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName

data class FeedTopicoResponseDto(
    @SerializedName("topico")
    val topicoResponseDto: TopicoResponseDto,
    val liked: Boolean,
    val commentSize: Int,
    val autor: ContaResponseDto
)