package com.yudawahfiudin.storyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.yudawahfiudin.storyapp.data.Resource
import com.yudawahfiudin.storyapp.remote.User
import com.yudawahfiudin.storyapp.data.paging.StoryPagingSource
import com.yudawahfiudin.storyapp.data.request.LoginRequest
import com.yudawahfiudin.storyapp.data.request.RegisterRequest
import com.yudawahfiudin.storyapp.preference.UserPreference
import com.yudawahfiudin.storyapp.remote.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception

class StoryRepository(private val pref: UserPreference, private val apiService: ApiService) {

    fun getStory(): LiveData<PagingData<ListStoryItem>>{
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                StoryPagingSource(apiService, pref)
            }
        ).liveData
    }

    fun userLogin(email: String, password: String): LiveData<Resource<LoginResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.login(LoginRequest(email, password))
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.d("Login", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun userRegister(name: String, email: String, password: String): LiveData<Resource<RegisterResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.register(
                RegisterRequest(name, email, password))
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ): LiveData<Resource<RegisterResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.addStory(token, file, description)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun getStoryLocation(token: String): LiveData<Resource<StoryResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getStoryLocation(token, 1)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun getUserData(): LiveData<User> {
        return pref.getUserData().asLiveData()
    }

    suspend fun saveUserData(user: User) {
        pref.saveUserData(user)
    }

    suspend fun login() {
        pref.login()
    }

    suspend fun logout() {
        pref.logout()
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            preferences: UserPreference,
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(preferences, apiService)
            }.also { instance = it }
    }
}