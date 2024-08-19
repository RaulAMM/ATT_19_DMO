package com.example.att_19.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.att_19.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username

    init{
        viewModelScope.launch {
            userRepository.usernameFlow.collect{
                _username.value = it
            }
        }
    }

    fun save(username: String){
        viewModelScope.launch {
            userRepository.saveUsername(username)
        }
    }

}