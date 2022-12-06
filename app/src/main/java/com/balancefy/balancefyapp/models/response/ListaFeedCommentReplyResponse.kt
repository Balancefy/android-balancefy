package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName

data class ListaFeedCommentReplyResponse(
    val message: String,
    @SerializedName("list")
    val listTopic: List<FeedCommentReplyResponseDto>
)