package com.marta.bookworm.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Token")
class Token(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "auth_token")
    val token: String
)