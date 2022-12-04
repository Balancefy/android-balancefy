package com.balancefy.balancefyapp.models.request

import com.google.gson.annotations.SerializedName

data class CommentReplyRequest(
    @SerializedName("conteudo")
    val content : String,
    @SerializedName("idTopico")
    val post: Int
)