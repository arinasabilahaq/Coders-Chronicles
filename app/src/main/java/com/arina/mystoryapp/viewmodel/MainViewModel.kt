package com.arina.mystoryapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arina.mystoryapp.data.model.Story
import com.arina.mystoryapp.data.model.User
import com.arina.mystoryapp.data.repo.StoryRepository

class MainViewModel (private val repository: StoryRepository) : ViewModel() {

    fun getStory(token: String): LiveData<PagingData<Story>> {
        return  repository.getStory(token).cachedIn(viewModelScope)
    }

    fun getUser(): LiveData<User> {
        return repository.getUserData()
    }
}