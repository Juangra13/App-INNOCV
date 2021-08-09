package com.example.myapplication.presenter

import com.example.myapplication.business.model.User
import com.example.myapplication.view.fragment.UserIView

interface UserListIPresenter {
    fun getView(): UserIView?
    fun loadViewData()
    fun refreshUsers()
    fun loadUsers(userList: ArrayList<User>, isRefresh: Boolean)
    fun deleteUser(userId: String, position: Int)
    fun resultDeleteUser(isSucessful: Boolean, idUser: Int)
    fun launchSnackbarMessage(message: String)
    fun editUserName(user: User, position: Int)
    fun actualiceUser(user: User, position: Int)
}