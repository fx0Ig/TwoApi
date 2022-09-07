package com.example.twoapi.di

import androidx.room.Room
import com.example.twoapi.data.UserDatabase
import com.example.twoapi.network.BASE_URL
import com.example.twoapi.network.DailyMotionApiService
import com.example.twoapi.network.GitHubApiService
import com.example.twoapi.overview.UserViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val appModule = module {
    single {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()


        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL).client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
            .build().create(GitHubApiService::class.java)
    }

    single {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build().create(DailyMotionApiService::class.java)
    }

    single {
        Room.databaseBuilder(androidApplication(), UserDatabase::class.java, "user_database")
            .build()
    }

    single { get<UserDatabase>().userDao() }

    viewModel {
        UserViewModel(get(),get(),get())
    }





}