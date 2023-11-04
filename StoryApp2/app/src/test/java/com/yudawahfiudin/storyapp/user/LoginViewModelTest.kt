package com.yudawahfiudin.storyapp.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.yudawahfiudin.storyapp.data.Resource
import com.yudawahfiudin.storyapp.login.LoginViewModel
import com.yudawahfiudin.storyapp.remote.LoginResponse
import com.yudawahfiudin.storyapp.remote.User
import com.yudawahfiudin.storyapp.repository.StoryRepository
import com.yudawahfiudin.storyapp.utils.DataDummy
import com.yudawahfiudin.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest{

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var loginViewModel: LoginViewModel
    private val dummyLogin = DataDummy.generateDummyLoginResponse()

    private val email = "email@gmail.com"
    private val password = "password"
    private val user = User("nama", "token", true)

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel (storyRepository)
    }

    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }
    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when user Login is Success`() {
        val expectedUser = MutableLiveData<Resource<LoginResponse>>()
        expectedUser.value = Resource.Success(dummyLogin)
        Mockito.`when`(storyRepository.userLogin(email, password)).thenReturn(expectedUser)

        val actualUser = loginViewModel.userLogin(email, password).getOrAwaitValue()

        Mockito.verify(storyRepository).userLogin(email, password)
        Assert.assertNotNull(actualUser)
        Assert.assertTrue(actualUser is Resource.Success)
    }

    @Test
    fun `when save user is success`() = runTest {
        loginViewModel.saveUser(user)
        Mockito.verify(storyRepository).saveUserData(user)
    }

}