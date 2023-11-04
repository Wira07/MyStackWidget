package com.yudawahfiudin.storyapp.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yudawahfiudin.storyapp.remote.User
import com.yudawahfiudin.storyapp.repository.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repository: StoryRepository) : ViewModel()  {

    fun addStory(token: String, file: MultipartBody.Part, description: RequestBody) = repository.addStory(token, file, description)

    fun getUser(): LiveData<User> {
        return repository.getUserData()
    }

}