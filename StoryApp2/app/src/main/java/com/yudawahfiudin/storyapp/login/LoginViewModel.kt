package com.yudawahfiudin.storyapp.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yudawahfiudin.storyapp.remote.User
import com.yudawahfiudin.storyapp.repository.StoryRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: StoryRepository) : ViewModel() {

    fun userLogin(email: String, password: String) = repository.userLogin(email, password)

    fun saveUser(user: User) {
        viewModelScope.launch {
            repository.saveUserData(user)
        }
    }


}
