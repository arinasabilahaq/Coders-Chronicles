package com.arina.mystoryapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.arina.mystoryapp.DummyData
import com.arina.mystoryapp.data.networking.response.BaseResponse
import com.arina.mystoryapp.data.repo.StoryRepository
import com.arina.mystoryapp.data.source.Resource
import com.arina.mystoryapp.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class AddStoryVMTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    private lateinit var postViewModel: AddStoryViewModel
    private val Token = DummyData.generateToken()
    private val Photo = DummyData.generateImages()
    private val Description = DummyData.generateDescription()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        postViewModel = AddStoryViewModel(storyRepository)
    }

    @Test
    fun `post success should return a non-null result`() {
        val expectedAddStory = MutableLiveData<Resource<BaseResponse>>().apply {
            value = Resource.Success(BaseResponse(false, "Upload Success"))
        }
        Mockito.`when`(storyRepository.addStory(Token, Photo, Description, null, null)).thenReturn(expectedAddStory)
        val actualAddStory = postViewModel.addStory(Token, Photo, Description).getOrAwaitValue()
        Mockito.verify(storyRepository).addStory(Token, Photo, Description, null, null)
        assertNotNull(actualAddStory)
    }


    @Test
    fun `post error should return an Error result`() {
        val expectedAddStory = MutableLiveData<Resource<BaseResponse>>().apply {
            value = Resource.Error("Error")
        }
        Mockito.`when`(storyRepository.addStory(Token, Photo, Description, null, null)).thenReturn(expectedAddStory)
        val actualAddStory = postViewModel.addStory(Token, Photo, Description).getOrAwaitValue()
        Mockito.verify(storyRepository).addStory(Token, Photo, Description, null, null)
        assertNotNull(actualAddStory)
        assertTrue(actualAddStory is Resource.Error)
    }
}