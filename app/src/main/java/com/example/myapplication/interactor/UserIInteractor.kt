package com.example.myapplication.interactor

import com.example.myapplication.business.model.User

interface UserIInteractor {
    fun getAllUser(isRefresh: Boolean)
    fun deleteUser(id: String, position: Int)
    fun getUserForId(idUser: String)
    fun actualizeUser(user: User, position: Int)
}