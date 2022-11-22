package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

open class Transaction(
    @SerializedName("valor")
    open val value: Double,
    @SerializedName("categoria")
    open val category: String,
    @SerializedName("descricao")
    open val description: String,
    @SerializedName("tipo")
    open val type: String,
    open val createdAt: LocalDateTime
)