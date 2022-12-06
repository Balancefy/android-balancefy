package com.balancefy.balancefyapp.models.response

data class RankResponse(
    val message : String,
    val rank: List<AccountRankResponseDto>
)

