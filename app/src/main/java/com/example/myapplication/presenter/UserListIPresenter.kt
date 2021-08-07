package com.example.myapplication.presenter

import com.example.myapplication.view.fragment.UserIView

interface UserListIPresenter {
    fun getView(): UserIView?
    fun loadViewData()
}