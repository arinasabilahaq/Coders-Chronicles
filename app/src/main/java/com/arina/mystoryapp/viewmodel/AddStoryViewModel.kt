package com.arina.mystoryapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arina.mystoryapp.data.model.User
import com.arina.mystoryapp.data.repo.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody? = null,
        lon: RequestBody? = null,
    ) = repository.addStory(token, file, description, lat, lon)

    fun getUser(): LiveData<User> = repository.getUserData()
}