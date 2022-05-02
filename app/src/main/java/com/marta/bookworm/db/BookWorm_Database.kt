package com.marta.bookworm.db

import android.content.Context
import com.marta.bookworm.db.entities.Token
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Token::class], version = 1)
abstract class BookWorm_Database: RoomDatabase() {
    abstract fun dao(): DatabaseDAO

    companion object{
        @Volatile
        private var INSTANCE: BookWorm_Database? = null

        fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext, BookWorm_Database::class.java,"Bookworm.db")
            .build()
    }
}