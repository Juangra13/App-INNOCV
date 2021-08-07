package com.example.myapplication.presenter

import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.business.api.ApiService
import com.example.myapplication.business.api.RetrofitBuilder
import com.example.myapplication.business.model.User
import com.example.myapplication.constants.NetworkConstants
import com.example.myapplication.view.fragment.UserIView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserListPresenter(view: UserIView, context: AppCompatActivity): UserListIPresenter {

    private var viewRef = view
    private val context = context
    private var userList = arrayListOf<User>()
    private val retrofit = RetrofitBuilder

    override fun getView(): UserIView? {
        return viewRef
    }

    override fun loadViewData() {
        viewRef.initRecyclerView(userList)
        loadAllUser()
    }

    private fun loadAllUser(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.getRetrofitGet().create(ApiService::class.java).getAllUser(NetworkConstants.GET_USERS)
            val userList = call.body()
            context.runOnUiThread {
                if(call.isSuccessful){
                    userList?.let { viewRef.initRecyclerView(it) } ?: { "show error" }
                }else{
                    //show error
                }
            }
        }
    }
}