package com.arina.mystoryapp

import com.arina.mystoryapp.data.model.Story
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object DummyData {

    fun generateToken(): String {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTk4NzY2Nzg5MiIsImlhdCI6MTY3MTM2OTA1Mn0.CIcJYmwctLHoJMcem1GBFjgbByNiNLfk4DmqCKnhGNM"
    }

    fun generateListStoryItems(): List<Story> {
        val items: MutableList<Story> = mutableListOf()
        for (i in 0..100) {
            val latitude = 8.109877 + (i * 0.001)
            val longitude = 98.85736 + (i * 0.001)
            val list = Story(
                "$i",
                "Story $i",
                "Description $i",
                "https://example.com/image_$i.jpg",
                latitude,
                longitude
            )
            items.add(list)
        }
        return items
    }


    fun generateImages(): MultipartBody.Part {
        val mediaType = "image/png".toMediaTypeOrNull()
            ?: throw IllegalStateException("Media type cannot be null")

        val requestBody = RequestBody.create(mediaType, "")
        return MultipartBody.Part.createFormData("image", "https://example.com/image.png", requestBody)
    }

    fun generateDescription(): RequestBody {
        val description = "Sample description"
        val mediaType = "text/plain".toMediaTypeOrNull()
            ?: throw IllegalStateException("Media type cannot be null")

        return description.toRequestBody(mediaType)
    }

}