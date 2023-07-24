package com.arina.mystoryapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arina.mystoryapp.data.model.User
import com.arina.mystoryapp.data.repo.StoryRepository

class MapsViewModel (private val repository: StoryRepository): ViewModel() {

    fun getStoryLocation(token: String) =
        repository.getStoryLocation(token)

    fun getUser(): LiveData<User> {
        return repository.getUserData()
    }
}