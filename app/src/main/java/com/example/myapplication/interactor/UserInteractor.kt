package com.example.myapplication.interactor

import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.business.api.ApiService
import com.example.myapplication.business.api.RetrofitBuilder
import com.example.myapplication.business.model.User
import com.example.myapplication.constants.NetworkConstants
import com.example.myapplication.presenter.UserListPresenter
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

class UserInteractor(userPresenter: UserListPresenter, context: AppCompatActivity): UserIInteractor {

    private val presenter: UserListPresenter = userPresenter
    private val context = context
    private val retrofit = RetrofitBuilder

    override fun getAllUser(isRefresh: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.getRetrofitGet().create(ApiService::class.java).getAllUser(
                NetworkConstants.GET_USERS)
            val userList = call.body()
            context.runOnUiThread {
                if(call.isSuccessful){
                    userList?.let { presenter.loadUsers(it, isRefresh) }
                }else{
                    presenter.launchSnackbarMessage("Error al obtener los usuarios")
                }
            }
        }
    }

    override fun deleteUser(idUser: String, position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.getRetrofitGet().create(ApiService::class.java).removeUser(
                idUser)
            context.runOnUiThread {
                presenter.resultDeleteUser(call.isSuccessful, position)
            }
        }
    }

    override fun getUserForId(idUser: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.getRetrofitGet().create(ApiService::class.java).getUserForId(idUser)
            val user = call.body()
            context.runOnUiThread {
                if(call.isSuccessful){
                    // presenter.refreshListWidthUser(user)
                }else{
                    presenter.launchSnackbarMessage("No se ha podido realizar la petición")
                }
            }
        }
    }

    override fun actualizeUser(user: User, position: Int) {
        val jsonString = Gson().toJson(user)
        val body = RequestBody.create(MediaType.parse(NetworkConstants.MEDIATYPE_PARSE), jsonString)
        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.getRetrofitGet().create(ApiService::class.java).editUser(body)
            context.runOnUiThread {
                if(call.isSuccessful){
                    presenter.actualiceUser(user, position)
                }else{
                    presenter.launchSnackbarMessage("No se ha podido actualizar el usuario ${user.name}")
                }
            }
        }
    }
}