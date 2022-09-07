package com.example.twoapi.network


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyMotionResponse(
    @SerialName("explicit")
    val explicit: Boolean = false,
    @SerialName("has_more")
    val hasMore: Boolean = false,
    @SerialName("limit")
    val limit: Int = 0,
    @SerialName("list")
    val list: List<DailyMotionUser> = listOf(),
    @SerialName("page")
    val page: Int = 0,
    @SerialName("total")
    val total: Int = 0
)