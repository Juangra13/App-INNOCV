package com.example.myapplication.presenter

import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.business.model.User
import com.example.myapplication.interactor.UserIInteractor
import com.example.myapplication.interactor.UserInteractor
import com.example.myapplication.view.fragment.UserIView

class UserListPresenter(view: UserIView, context: AppCompatActivity): UserListIPresenter {

    private var viewRef = view
    private val interactor: UserIInteractor = UserInteractor(this, context)

    override fun getView(): UserIView? {
        return viewRef
    }

    override fun loadViewData() {
        loadAllUser(false)
    }

    override fun refreshUsers() {
        loadAllUser(true)
    }

    override fun loadUsers(userList: ArrayList<User>, isRefresh: Boolean) {
        if(isRefresh) {
            viewRef.actualizeAllList(userList)
        } else {
            viewRef.initRecyclerView(userList)
        }
        viewRef.hideKeyboard()
    }

    override fun deleteUser(userId: String, position: Int) {
        interactor.deleteUser(userId, position)
    }

    override fun resultDeleteUser(isSucessful: Boolean, position: Int) {
        if(isSucessful) {
            viewRef.removeUser(position)
        } else {
            launchSnackbarMessage("Noo se ha podido eliminar el usuario, intentelo mas tarde.")
        }
    }

    private fun loadAllUser(isRefresh: Boolean){
        interactor.getAllUser(isRefresh)
    }

    override fun launchSnackbarMessage(message: String) {
        viewRef.hideLoading()
        viewRef.showMessageSnackbar(message)
    }

    override fun editUserName(user: User, position: Int) {
        interactor.actualizeUser(user, position)
    }

    override fun actualiceUser(user: User, position: Int) {
        viewRef.actualizeUserList(user, position)
    }
}