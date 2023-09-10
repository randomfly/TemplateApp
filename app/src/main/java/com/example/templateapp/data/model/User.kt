package com.example.templateapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey
    val id: Int,
    val uid: String,
    val first_name: String,
    val last_name: String,
    val username: String,
    val email: String,
    val avatar: String
)