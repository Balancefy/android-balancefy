package com.balancefy.balancefyapp.models.response

data class CommentReplyResponseDto (
    val name: String,
    val description: String,
    val id: Int,
    val createdAt: String,
    val message: String,
    val likes: Int
    )