package com.example.mystoryapp.di

import android.content.Context
import com.example.mystoryapp.api.APIConfig
import com.example.mystoryapp.database.StoryDatabase
import com.example.mystoryapp.repository.MainRepository

object Injection {
    fun provideRepository(context: Context): MainRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = APIConfig.getApiService()
        return MainRepository(database, apiService)
    }
}