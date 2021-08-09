package com.example.myapplication.interactor

import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.business.api.ApiService
import com.example.myapplication.business.api.RetrofitBuilder
import com.example.myapplication.business.model.User
import com.example.myapplication.constants.NetworkConstants
import com.example.myapplication.presenter.HighUserPresenter
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

class HighUserInteractor(highUserPresenter: HighUserPresenter, context: AppCompatActivity) :
    HighUserIInteractor {
    private val presenter: HighUserPresenter = highUserPresenter
    private val context = context
    private val retrofit = RetrofitBuilder

    override fun addNewUser(user: User) {
        val jsonString = Gson().toJson(user)
        val body =
            RequestBody.create(MediaType.parse(NetworkConstants.MEDIATYPE_PARSE), jsonString)
        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.getRetrofitGet().create(ApiService::class.java).addNewUser(body)
            context.runOnUiThread {
                if (call.isSuccessful) {
                    presenter.responseOK()
                } else {
                    presenter.responseKO()
                }
            }
        }
    }
}