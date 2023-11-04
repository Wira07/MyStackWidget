package com.yudawahfiudin.storyapp.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.yudawahfiudin.storyapp.data.Resource
import com.yudawahfiudin.storyapp.register.RegisterViewModel
import com.yudawahfiudin.storyapp.remote.RegisterResponse
import com.yudawahfiudin.storyapp.repository.StoryRepository
import com.yudawahfiudin.storyapp.utils.DataDummy
import com.yudawahfiudin.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest{

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var registerViewModel: RegisterViewModel
    private val dummyRegister = DataDummy.generateDummyRegisterResponse()

    private val name = "Name"
    private val email = "email@gmail.com"
    private val password = "password"

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(storyRepository)
    }

    @Test
    fun `when user Register is Success`() {
        val expectedUser = MutableLiveData<Resource<RegisterResponse>>()
        expectedUser.value = Resource.Success(dummyRegister)
        Mockito.`when`(storyRepository.userRegister(name, email, password)).thenReturn(expectedUser)

        val actualUser = registerViewModel.userRegister(name, email, password).getOrAwaitValue()

        Mockito.verify(storyRepository).userRegister(name, email, password)
        Assert.assertNotNull(actualUser)
        Assert.assertTrue(actualUser is Resource.Success)
    }
}