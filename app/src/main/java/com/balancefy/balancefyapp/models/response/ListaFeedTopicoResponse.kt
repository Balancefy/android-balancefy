package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName

data class ListaFeedTopicoResponse(
    val message: String,
    @SerializedName("list")
    val listTopic: List<FeedTopicoResponseDto>
)