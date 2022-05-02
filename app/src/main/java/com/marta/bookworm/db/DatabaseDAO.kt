package com.marta.bookworm.db

import androidx.room.*
import com.marta.bookworm.db.entities.Token

@Dao
interface DatabaseDAO {
    //Token
    @Query("SELECT * FROM Token")
    suspend fun findAllToken(): List<Token>

    @Query("SELECT * FROM Token WHERE token.email = :token")
    suspend fun findMyToken(token: String): Token

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToken(token: Token)

    @Delete
    suspend fun delteToken(token: Token)

}