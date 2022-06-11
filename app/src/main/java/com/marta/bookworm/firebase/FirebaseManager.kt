package com.marta.bookworm.firebase

import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseManager {
    @Provides
    @Singleton
    fun getFirebaseService(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }
}