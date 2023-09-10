package com.example.templateapp.data.source.repository

import androidx.room.withTransaction
import com.example.templateapp.data.source.local.UserDatabase
import com.example.templateapp.data.source.remote.api.ApiService
import com.example.templateapp.utils.NetworkHelper
import com.example.templateapp.utils.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val api: ApiService,
    private val db: UserDatabase,
    private val networkHelper: NetworkHelper
) : UserRepository {

    private val userDao = db.userDao()

    override suspend fun getUsers() = networkBoundResource(
        query = {
            userDao.getAllUsers()
        },
        fetch = {
            delay(2000)
            api.getUsers()
        },
        saveFetchResult = { users ->
            db.withTransaction {
                userDao.deleteAllUsers()
                userDao.insertUsers(users)
            }
        },
        shouldFetch = {
            networkHelper.isNetworkAvailable()
        },
        onFetchFailed = {
        }
    )
}