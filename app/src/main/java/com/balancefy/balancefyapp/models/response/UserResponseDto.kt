package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    val id: Int,
    @SerializedName("nome")
    val name: String,
    @SerializedName("dataNasc")
    val birthDate: String,
    val avatar: String,
    val banner: String,
    @SerializedName("tipo")
    val type: TypeUser
)

enum class TypeUser{
    DEFAULT,
    GOOGLE,
    FACEBOOK,
    GITHUB
}