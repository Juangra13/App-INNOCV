package com.example.myapplication.business.api

import com.example.myapplication.business.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getAllUser(@Url url: String): Response<ArrayList<User>>
}