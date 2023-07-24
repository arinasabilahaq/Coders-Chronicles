package com.arina.mystoryapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.arina.mystoryapp.data.networking.api.ApiConfig
import com.arina.mystoryapp.data.preferences.UserPreferences
import com.arina.mystoryapp.data.repo.StoryRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("MyStoryApp")

object StoryInjection {
    fun provideRepository(context: Context): StoryRepository {
        val dataStore = context.dataStore
        val preferences = UserPreferences.getInstance(dataStore)
        val apiService = ApiConfig.getApiClient()
        return StoryRepository.getInstance(preferences, apiService)
    }
}