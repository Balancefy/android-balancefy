package com.balancefy.balancefyapp.models.response

import com.balancefy.balancefyapp.models.response.GoalsResponse
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class TransactionResponse(
    override val value: Double,
    override val category: String,
    override val description: String,
    override val type: String,
    override val createdAt: LocalDateTime,
    val goal: GoalsResponse
): Transaction(
    value,
    category,
    description,
    type,
    createdAt
)