package com.example.twoapi.network

import retrofit2.http.GET

const val BASE_URL = "https://api.dailymotion.com/users/"

interface DailyMotionApiService{
    @GET(BASE_URL)
    suspend fun getApiResponse(): DailyMotionResponse
}
