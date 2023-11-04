package com.yudawahfiudin.storyapp.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yudawahfiudin.storyapp.remote.User
import com.yudawahfiudin.storyapp.repository.StoryRepository


class MapsViewModel(private val repository: StoryRepository): ViewModel() {

    fun getStoryLocation(token: String) =
        repository.getStoryLocation(token)

    fun getUser(): LiveData<User> {
        return repository.getUserData()
    }
}