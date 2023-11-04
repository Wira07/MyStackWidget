package com.yudawahfiudin.storyapp.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.yudawahfiudin.storyapp.add.AddStoryViewModel
import com.yudawahfiudin.storyapp.data.Resource
import com.yudawahfiudin.storyapp.remote.RegisterResponse
import com.yudawahfiudin.storyapp.remote.User
import com.yudawahfiudin.storyapp.repository.StoryRepository
import com.yudawahfiudin.storyapp.utils.DataDummy
import com.yudawahfiudin.storyapp.utils.getOrAwaitValue
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var addStoryViewModel: AddStoryViewModel
    private val dummyAddStory = DataDummy.generateDummyAddStoryResponse()
    private val dummyGetUser = DataDummy.generateDummyGetUserResponse()
    private val token = "TOKEN"
    private val user = User("nama", "token", true)

    @Before
    fun setUp() {
        addStoryViewModel = AddStoryViewModel(storyRepository)
    }

    @Test
    fun `when Add Story is Success`() {
        val description = "description".toRequestBody("text/plain".toMediaType())
        val file = Mockito.mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )

        val expectedStory = MutableLiveData<Resource<RegisterResponse>>()
        expectedStory.value = Resource.Success(dummyAddStory)
        Mockito.`when`(storyRepository.addStory(token, imageMultipart, description)).thenReturn(expectedStory)

        val actualStory = addStoryViewModel.addStory(token, imageMultipart, description).getOrAwaitValue()

        Mockito.verify(storyRepository).addStory(token, imageMultipart, description)
        Assert.assertNotNull(actualStory)
        Assert.assertTrue(actualStory is Resource.Success)
    }


    @Test
    fun `when get user is Success`() {
        val expectedUser = MutableLiveData<User>()
        expectedUser.value = user
        Mockito.`when`(storyRepository.getUserData()).thenReturn(expectedUser)

        val actualUser = addStoryViewModel.getUser().getOrAwaitValue()

        Mockito.verify(storyRepository).getUserData()
        Assert.assertNotNull(actualUser)
        assertEquals(expectedUser.value, actualUser)
    }
}