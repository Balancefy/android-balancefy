package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName

data class FeedCommentReplyResponseDto (
    @SerializedName("comentario")
    val commentReplyDtio: CommentReplyResponseDto,
    val liked: Boolean,
    val autor: ContaResponseDto
)