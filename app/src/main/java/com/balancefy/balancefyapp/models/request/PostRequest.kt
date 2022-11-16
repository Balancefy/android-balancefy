package com.balancefy.balancefyapp.models.request

import com.google.gson.annotations.SerializedName

data class PostRequest(
    @SerializedName("titulo")
    val title : String,
    @SerializedName("conteudo")
    val content : String
)