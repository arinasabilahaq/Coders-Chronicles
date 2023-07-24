package com.arina.mystoryapp.ui.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arina.mystoryapp.data.model.Story
import com.arina.mystoryapp.databinding.ActivityDetailStoryBinding
import com.bumptech.glide.Glide

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    companion object{
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater )
        setContentView(binding.root)
        supportActionBar?.hide()

        viewSetup()
    }

    private fun viewSetup() {
        val detail = intent.getParcelableExtra<Story>(EXTRA_DETAIL)

        binding.apply {
            tvNameDetail.text = detail?.name
            tvDesc.text = detail?.description
        }
        Glide.with(this)
            .load(detail?.photoUrl)
            .into(binding.imgStoryDetail)
    }
}