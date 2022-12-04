package com.balancefy.balancefyapp.models.request

import com.google.gson.annotations.SerializedName

data class UserEdit(
    val email: String,
    @SerializedName("nome")
    val name: String
)