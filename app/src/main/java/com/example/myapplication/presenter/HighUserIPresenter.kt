package com.example.myapplication.presenter

import com.example.myapplication.view.fragment.HighUserIView

interface HighUserIPresenter {
    fun getView(): HighUserIView
    fun addNewUser(name: String, birthday: String)
    fun responseOK()
    fun responseKO()
}