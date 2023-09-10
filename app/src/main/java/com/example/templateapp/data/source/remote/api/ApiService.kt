package com.example.templateapp.data.source.remote.api

import com.example.templateapp.data.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users/random_user?size=10")
    suspend fun getUsers(): List<User>
}