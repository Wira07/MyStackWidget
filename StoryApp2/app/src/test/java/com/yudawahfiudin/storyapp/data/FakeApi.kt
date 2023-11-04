package com.yudawahfiudin.storyapp.data

import com.yudawahfiudin.storyapp.data.request.LoginRequest
import com.yudawahfiudin.storyapp.data.request.RegisterRequest
import com.yudawahfiudin.storyapp.remote.ApiService
import com.yudawahfiudin.storyapp.remote.LoginResponse
import com.yudawahfiudin.storyapp.remote.RegisterResponse
import com.yudawahfiudin.storyapp.remote.StoryResponse
import com.yudawahfiudin.storyapp.utils.DataDummy
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApi: ApiService {
    private val dummyStoryResponse = DataDummy.generateDummyStoryLocation()
    private val dummyRegisterResponse = DataDummy.generateDummyRegisterResponse()
    private val dummyLoginResponse = DataDummy.generateDummyLoginResponse()
    private val dummyAddStoryResponse = DataDummy.generateDummyAddStoryResponse()

    override suspend fun register(request: RegisterRequest): RegisterResponse {
        return  dummyRegisterResponse
    }

    override suspend fun login(request: LoginRequest): LoginResponse {
        return dummyLoginResponse
    }

    override suspend fun getStory(token: String, page: Int, size: Int): StoryResponse {
        return dummyStoryResponse
    }

    override suspend fun getStoryLocation(
        token: String,
        location: Int,
    ): StoryResponse {
        return dummyStoryResponse
    }

    override suspend fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ): RegisterResponse {
        return dummyAddStoryResponse
    }
}