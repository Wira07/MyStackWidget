package com.yudawahfiudin.storyapp.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.yudawahfiudin.storyapp.add.AddStoryViewModel
import com.yudawahfiudin.storyapp.data.Resource
import com.yudawahfiudin.storyapp.remote.StoryResponse
import com.yudawahfiudin.storyapp.remote.User
import com.yudawahfiudin.storyapp.repository.StoryRepository
import com.yudawahfiudin.storyapp.utils.DataDummy
import com.yudawahfiudin.storyapp.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mapsViewModel: MapsViewModel
    private val dummyStoryLocation = DataDummy.generateDummyStoryLocation()
    private val token = "TOKEN"
    private val user = User("nama", "token", true)

    @Before
    fun setUp() {
        mapsViewModel = MapsViewModel(storyRepository)
    }

    @Test
    fun `when get Story Location is success`() {
        val expectedStory = MutableLiveData<Resource<StoryResponse>>()
        expectedStory.value = Resource.Success(dummyStoryLocation)

        Mockito.`when`(storyRepository.getStoryLocation(token)).thenReturn(expectedStory)

        val actualStory = mapsViewModel.getStoryLocation(token).getOrAwaitValue()
        Mockito.verify(storyRepository).getStoryLocation(token)
        Assert.assertNotNull(actualStory)
        Assert.assertTrue(actualStory is Resource.Success)
    }

    @Test
    fun `when get user is Success`() {
        val expectedUser = MutableLiveData<User>()
        expectedUser.value = user
        Mockito.`when`(storyRepository.getUserData()).thenReturn(expectedUser)

        val actualUser = mapsViewModel.getUser().getOrAwaitValue()

        Mockito.verify(storyRepository).getUserData()
        Assert.assertNotNull(actualUser)
        assertEquals(expectedUser.value, actualUser)
    }
}