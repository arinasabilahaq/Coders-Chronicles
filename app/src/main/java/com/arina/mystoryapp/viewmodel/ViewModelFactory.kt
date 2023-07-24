package com.arina.mystoryapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arina.mystoryapp.data.repo.StoryRepository
import com.arina.mystoryapp.di.StoryInjection
import com.arina.mystoryapp.ui.maps.MapsViewModel

class ViewModelFactory(private val repository: StoryRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(repository)
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository)
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> AddStoryViewModel(repository)
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> MapsViewModel(repository)
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.simpleName)
        }
        return viewModel as T
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: createInstance(context).also { instance = it }
            }
        }

        private fun createInstance(context: Context): ViewModelFactory {
            val repository = StoryInjection.provideRepository(context)
            return ViewModelFactory(repository)
        }
    }
}
