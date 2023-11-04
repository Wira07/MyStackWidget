package com.yudawahfiudin.storyapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.yudawahfiudin.storyapp.adapter.StoriesAdapter
import com.yudawahfiudin.storyapp.preference.UserPreference
import com.yudawahfiudin.storyapp.remote.ApiService
import com.yudawahfiudin.storyapp.remote.ListStoryItem
import com.yudawahfiudin.storyapp.repository.StoryRepository
import com.yudawahfiudin.storyapp.story.StoryPagingSource
import com.yudawahfiudin.storyapp.utils.DataDummy
import com.yudawahfiudin.storyapp.utils.MainDispatcherRule
import com.yudawahfiudin.storyapp.utils.getOrAwaitValue
import com.yudawahfiudin.storyapp.utils.observeForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryImplTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var pref: UserPreference

    @Mock
    private lateinit var storyRepository: StoryRepository

    @Before
    fun setUp() {
        apiService = FakeApi()
        storyRepository = StoryRepository( pref, apiService)
    }

    @Test
    fun `when user Register is success`() = runTest {
        val expectedUser = DataDummy.generateDummyRegisterResponse()
        val actualUser = storyRepository.userRegister(name, email, password)
        actualUser.observeForTesting {
            Assert.assertNotNull(actualUser)
            Assert.assertEquals(expectedUser.message, (actualUser.value as Resource.Success).data.message)
        }
    }


    @Test
    fun `when user Login is success`() = runTest {
        val expectedUser = DataDummy.generateDummyLoginResponse()
        val actualUser = storyRepository.userLogin(email, password)
        actualUser.observeForTesting {
            Assert.assertNotNull(actualUser)
            Assert.assertEquals(expectedUser.message, (actualUser.value as Resource.Success).data.message)
        }
    }

    @Test
    fun `when get Story is success`() = runTest {
        val mockedClass = Mockito.mock(StoryRepository::class.java)
        val expectedStory = MutableLiveData<PagingData<ListStoryItem>>()
        expectedStory.value = StoryPagingSource.snapshot(DataDummy.generateDummyStory())

        Mockito.`when`(mockedClass.getStory()).thenReturn(expectedStory)

        val actualData = mockedClass.getStory().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.DIFF_CALLBACK,
            updateCallback = object : ListUpdateCallback {
                override fun onInserted(position: Int, count: Int) {}
                override fun onRemoved(position: Int, count: Int) {}
                override fun onMoved(fromPosition: Int, toPosition: Int) {}
                override fun onChanged(position: Int, count: Int, payload: Any?) {}
            },
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualData)

        Mockito.verify(mockedClass).getStory()

        Assert.assertNotNull(actualData)
        Assert.assertEquals(DataDummy.generateDummyStory(), differ.snapshot())
        Assert.assertEquals(DataDummy.generateDummyStory().size, differ.snapshot().size)
    }

    @Test
    fun `when get Story Location is success`() = runTest{
        val expectedStory = DataDummy.generateDummyStoryLocation()
        val actualStory = storyRepository.getStoryLocation(token)
        actualStory.observeForTesting {
            Assert.assertNotNull(actualStory)
            Assert.assertEquals(expectedStory.listStory.size, (actualStory.value as Resource.Success).data.listStory.size)
        }
    }

    @Test
    fun `when add Story is success`() = runTest{
        val description = "description".toRequestBody("text/plain".toMediaType())

        val file = Mockito.mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )

        val expectedStory = DataDummy.generateDummyAddStoryResponse()
        val actualStory = storyRepository.addStory(token, imageMultipart, description)
        actualStory.observeForTesting {
            Assert.assertNotNull(actualStory)
            Assert.assertEquals(expectedStory.message, (actualStory.value as Resource.Success).data.message)
        }
    }

    companion object {
        private const val token = "TOKEN"
        private const val name = "Name"
        private const val email = "email@gmail.com"
        private const val password = "password"
    }
}