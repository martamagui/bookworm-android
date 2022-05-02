package com.marta.bookworm.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DBModule {
    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): BookWorm_Database{
        return Room.databaseBuilder(context, BookWorm_Database::class.java,"bookworm-database.db").build()
    }
    @Provides
    @Singleton
    fun provideRepositoryDao(db: BookWorm_Database): DatabaseDAO{
        return db.dao()
    }
}