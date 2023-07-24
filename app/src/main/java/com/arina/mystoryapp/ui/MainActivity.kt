package com.arina.mystoryapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.arina.mystoryapp.adapter.LoadingStateAdapter
import com.arina.mystoryapp.adapter.StoryAdapter
import com.arina.mystoryapp.databinding.ActivityMainBinding
import com.arina.mystoryapp.ui.maps.MapsActivity
import com.arina.mystoryapp.ui.story.AddStoryActivity
import com.arina.mystoryapp.ui.user.LoginActivity
import com.arina.mystoryapp.viewmodel.MainViewModel
import com.arina.mystoryapp.viewmodel.UserViewModel
import com.arina.mystoryapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMVModel()
        setupView()
        onClick()
    }

    private fun onClick() {
        binding.fabLogOut.setOnClickListener(::onClickLogOut)
        binding.fabAddStory.setOnClickListener(::onClickAddStory)
        binding.fabMaps.setOnClickListener(::onClickMaps)

    }

    private fun onClickLogOut(view: View) {
        userViewModel.logout()
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }

    private fun onClickAddStory(view: View) {
        startActivity(Intent(this, AddStoryActivity::class.java))
    }

    private fun onClickMaps(view: View) {
        startActivity(Intent(this, MapsActivity::class.java))
    }


    private fun setupView() {
        storyAdapter = StoryAdapter()
        mainViewModel.getUser().observe(this@MainActivity) { user ->
            if (user.isLogin) {
                setStory(user.token)
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        with(binding.rvStory) {
            setHasFixedSize(true)
            adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                })
        }

    }

    private fun setStory(token: String) {
        mainViewModel.getStory(token).observe(this@MainActivity) {
            storyAdapter.submitData(lifecycle, it)
            showProgressIndicator(false)
        }
    }

    private fun setupMVModel() {
        val Factory = ViewModelFactory.getInstance(this)

        mainViewModel = ViewModelProvider(this, Factory).get(MainViewModel::class.java)
        userViewModel = ViewModelProvider(this, Factory).get(UserViewModel::class.java)
    }

    private fun showProgressIndicator(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}