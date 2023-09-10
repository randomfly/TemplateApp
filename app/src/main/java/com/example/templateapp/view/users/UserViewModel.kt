package com.example.templateapp.view.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templateapp.data.model.User
import com.example.templateapp.data.source.repository.UserRepository
import com.example.templateapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    // val restaurants = repository.getRestaurants().asLiveData()
    val user = MutableLiveData<Resource<List<User>>>()

    init {
        getUsers()
    }

    fun getUsers() = viewModelScope.launch {
        repository.getUsers().collect {
            user.value = it
        }
    }
}