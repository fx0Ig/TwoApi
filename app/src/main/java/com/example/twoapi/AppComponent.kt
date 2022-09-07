package com.example.twoapi

import com.example.twoapi.data.UserDatabase
import com.example.twoapi.network.BASE_URL
import com.example.twoapi.network.DailyMotionApiService
import com.example.twoapi.network.GitHubApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.HTTP
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

object AppComponent {

    lateinit var userDatabase: UserDatabase

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val githubApiService =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL).client(OkHttpClient.Builder().addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val builder = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer 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")

                    return chain.proceed(builder.build())
                }

            }
            ).addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
            .build().create(GitHubApiService::class.java)

    val dailymotionApiService =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build().create(DailyMotionApiService::class.java)
}

