package com.example.myapplication.view.fragment

import com.example.myapplication.business.model.User

interface UserIView {
    fun initRecyclerView(userList: ArrayList<User>)
}