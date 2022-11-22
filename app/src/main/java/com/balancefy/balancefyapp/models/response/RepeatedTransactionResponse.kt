package com.balancefy.balancefyapp.models.response

import java.time.LocalDateTime

data class RepeatedTransactionResponse(
    override val value: Double,
    override val category: String,
    override val description: String,
    override val type: String,
    override val createdAt: LocalDateTime
): Transaction(
    value,
    category,
    description,
    type,
    createdAt
)