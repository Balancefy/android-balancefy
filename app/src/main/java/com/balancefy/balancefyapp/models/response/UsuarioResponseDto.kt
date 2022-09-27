package com.balancefy.balancefyapp.models.response

import java.time.LocalDate

data class UsuarioResponseDto(
    val id: Int,
    val nome: String,
    val dataNasc: String,
    val avatar: String,
    val banner: String,
    val tipo: TypeUser

)

enum class TypeUser{
    DEFAULT,
    GOOGLE,
    FACEBOOK,
    GITHUB
}