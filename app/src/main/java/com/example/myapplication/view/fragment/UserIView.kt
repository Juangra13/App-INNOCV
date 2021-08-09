package com.example.myapplication.view.fragment

import com.example.myapplication.business.model.User

interface UserIView {
    fun initRecyclerView(userList: ArrayList<User>)
    fun showLoading()
    fun hideLoading()
    fun hideKeyboard()
    fun removeUser(position: Int)
    fun showMessageSnackbar(message: String)
    fun actualizeUserList(user: User, position: Int)
    fun actualizeAllList(userList: ArrayList<User>)
}