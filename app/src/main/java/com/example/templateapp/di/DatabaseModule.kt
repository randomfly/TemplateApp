package com.example.templateapp.di

import android.content.Context
import androidx.room.Room
import com.example.templateapp.data.source.local.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): UserDatabase =
        Room.databaseBuilder(
            context,UserDatabase::class.java,
            "user_db"
        )
            .fallbackToDestructiveMigration()
            .build()
}
