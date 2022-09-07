package com.example.twoapi.network

import retrofit2.http.GET

const val GITHUB_BASE_URL = "https://api.github.com/users"

interface GitHubApiService{
    @GET(GITHUB_BASE_URL)
    suspend fun getApiResponse(): List<GithubResponseItem>
}
