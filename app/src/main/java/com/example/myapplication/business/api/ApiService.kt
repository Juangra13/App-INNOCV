package com.example.myapplication.business.api

import com.example.myapplication.business.model.User
import com.example.myapplication.constants.NetworkConstants
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getAllUser(@Url url: String): Response<ArrayList<User>>

    @GET
    suspend fun getUserForId(@Url url: String): Response<User>

    @DELETE(NetworkConstants.USER_MORE_ID)
    suspend fun removeUser(@Path(NetworkConstants.USER_ID) id: String): Response<Unit>

    @POST(NetworkConstants.GET_USERS)
    suspend fun addNewUser(@Body requestBody: RequestBody): Response<Unit>

    @PUT(NetworkConstants.GET_USERS)
    suspend fun editUser(@Body requestBody: RequestBody): Response<Unit>

}