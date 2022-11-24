package com.balancefy.balancefyapp.models.response

import com.google.gson.annotations.SerializedName

data class GoalsDetailsResponse(
    @SerializedName("objetivo")
    val goal: GoalsResponse,
    val tasks: List<TaskResponse>
)
