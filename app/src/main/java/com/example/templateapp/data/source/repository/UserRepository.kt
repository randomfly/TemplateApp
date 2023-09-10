package com.example.templateapp.data.source.repository

import com.example.templateapp.data.model.User
import com.example.templateapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(): Flow<Resource<List<User>>>
}