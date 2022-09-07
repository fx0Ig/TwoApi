package com.example.twoapi.network


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyMotionUser(
    @SerialName("id")
    val id: String = "",
    @SerialName("screenname")
    val screenname: String = ""
)