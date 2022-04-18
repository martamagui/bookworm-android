package com.marta.bookworm.di

import com.marta.bookworm.api.NetworkManagerAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideInjectedClassOne(): NetworkManagerAPI{
        return NetworkManagerAPI
    }
}