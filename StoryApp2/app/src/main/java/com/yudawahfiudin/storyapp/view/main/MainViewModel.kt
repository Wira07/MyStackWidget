package com.yudawahfiudin.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yudawahfiudin.storyapp.remote.User
import com.yudawahfiudin.storyapp.remote.ListStoryItem
import com.yudawahfiudin.storyapp.repository.StoryRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: StoryRepository) : ViewModel(){

    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        return  repository.getStory().cachedIn(viewModelScope)
    }

    fun getUser(): LiveData<User> {
        return repository.getUserData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}