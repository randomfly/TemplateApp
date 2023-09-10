package com.example.templateapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.templateapp.data.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
