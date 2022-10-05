package com.balancefy.balancefyapp.models.request

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class UserRegisterRequest(
    @SerializedName("nome")
    val name: String?,
    val email: String?,
    @SerializedName("senha")
    val pass: String?,
    @SerializedName("dataNasc")
    val birthDate: String
)
