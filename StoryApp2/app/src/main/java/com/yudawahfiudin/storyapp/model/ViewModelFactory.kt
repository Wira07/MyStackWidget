package com.yudawahfiudin.storyapp.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yudawahfiudin.storyapp.add.AddStoryViewModel
import com.yudawahfiudin.storyapp.injection.Injection
import com.yudawahfiudin.storyapp.login.LoginViewModel
import com.yudawahfiudin.storyapp.maps.MapsViewModel
import com.yudawahfiudin.storyapp.register.RegisterViewModel
import com.yudawahfiudin.storyapp.repository.StoryRepository
import com.yudawahfiudin.storyapp.view.main.MainViewModel


class ViewModelFactory(private val repository: StoryRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(repository) as T
            }
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                return RegisterViewModel(repository) as T
            }
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(repository) as T
            }
            if(modelClass.isAssignableFrom(AddStoryViewModel::class.java) ) {
                return AddStoryViewModel(repository) as T
            }
            if(modelClass.isAssignableFrom(MapsViewModel::class.java) ) {
                return MapsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)

    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }

}