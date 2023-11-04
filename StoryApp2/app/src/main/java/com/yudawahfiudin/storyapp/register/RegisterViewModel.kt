package com.yudawahfiudin.storyapp.register

import androidx.lifecycle.ViewModel
import com.yudawahfiudin.storyapp.repository.StoryRepository

class RegisterViewModel(private val repository: StoryRepository) : ViewModel() {

    fun userRegister(name: String, email: String, password: String) =
        repository.userRegister(name, email, password)
}
