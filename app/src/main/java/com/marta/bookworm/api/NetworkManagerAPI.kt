package com.marta.bookworm.api

import com.marta.bookworm.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkManagerAPI {
    @Provides
    @Singleton
    fun getHttpLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun getClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }
    @Provides
    @Singleton
    fun getRetrofitService(client: OkHttpClient): NetworkService {
        val retrofit = Retrofit.Builder()
            .baseUrl("Your Api URL")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        val service = retrofit.create(NetworkService::class.java)
        return service
    }
}