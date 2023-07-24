package com.arina.mystoryapp.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.arina.mystoryapp.data.source.Resource
import com.arina.mystoryapp.data.model.Story
import com.arina.mystoryapp.data.model.User
import com.arina.mystoryapp.data.networking.api.ApiService
import com.arina.mystoryapp.data.networking.response.BaseResponse
import com.arina.mystoryapp.data.networking.response.LoginResponse
import com.arina.mystoryapp.data.networking.response.StoryResponse
import com.arina.mystoryapp.data.preferences.UserPreferences
import com.arina.mystoryapp.data.request.LoginRequest
import com.arina.mystoryapp.data.request.RegisterRequest
import com.arina.mystoryapp.data.source.StoryPagingSource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception

class StoryRepository (private val pref: UserPreferences, private val apiService: ApiService) {

    fun getStory(token: String): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                StoryPagingSource(apiService, pref)
            }
        ).liveData
    }

    fun userLogin(email: String, password: String): LiveData<Resource<LoginResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.login(LoginRequest(email, password))
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.d("Login", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }


    fun userRegister(name: String, email: String, password: String): LiveData<Resource<BaseResponse>> = liveData {
        try {
            emit(Resource.Loading)
            val response = apiService.register(RegisterRequest(name, email, password))
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody? = null,
        lon: RequestBody? = null,
    ): LiveData<Resource<BaseResponse>> = liveData {
        try {
            emit(Resource.Loading)
            val response = apiService.addStory(token, file, description, lat, lon)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            val errorMsg = e.message.toString()
            Log.d("Signup", errorMsg)
            emit(Resource.Error(errorMsg))
        }
    }


    fun getStoryLocation(token: String): LiveData<Resource<StoryResponse>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getStoryLocation(token, 1)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }

    fun getUserData(): LiveData<User> {
        return pref.getUserData().asLiveData()
    }

    suspend fun saveUserData(user: User) {
        pref.saveUserData(user)
    }

    suspend fun logout() {
        pref.logout()
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            preferences: UserPreferences,
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(preferences, apiService)
            }.also { instance = it }
    }
}