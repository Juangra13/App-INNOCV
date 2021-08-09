package com.example.myapplication.presenter

import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.business.model.User
import com.example.myapplication.interactor.HighUserIInteractor
import com.example.myapplication.interactor.HighUserInteractor
import com.example.myapplication.utils.DateUtils
import com.example.myapplication.view.fragment.HighUserIView

class HighUserPresenter(view: HighUserIView, context: AppCompatActivity): HighUserIPresenter {

    private var viewRef = view
    private val interactor: HighUserIInteractor = HighUserInteractor(this, context)

    override fun getView(): HighUserIView {
        return viewRef
    }

    override fun addNewUser(name: String, birthday: String) {
        val newUser = User(name, birthday, 0)
        if(!name.isNullOrEmpty()) {
            newUser.name = name
        } else {
            return
        }
        if(!birthday.isNullOrEmpty()) {
            newUser.birthdate = DateUtils.formatDateToServer(birthday)
        } else {
            return
        }
        interactor.addNewUser(newUser)
    }

    override fun responseOK() {
        viewRef.showDialogOK()
    }

    override fun responseKO() {
        viewRef.showDialogKO()
    }
}