package com.example.mvp_homework

import android.os.Handler
import android.os.Looper

class Presenter: Contract.Presenter {

    private var view: Contract.View? = null
    private val uiHandler = Handler(Looper.getMainLooper())
    private var currentResult: Boolean = false
    private var errorText: String = ""
    private var passwordSaved = MainActivity::savedPassword



    override fun onAttach(view: Contract.View) {
        this.view = view
        if(currentResult) {
            view.setSuccess()
        } else {
            view.setError(errorText)
        }
    }

    override fun onLogin(login: String, password: String) {
        view?.showProgress()
        Thread {
            Thread.sleep(3_000)
            uiHandler.post {
                view?.hideProgress()
                if(checkCredentials(login, password)) {
                    view?.setSuccess()
                    currentResult = true
                    errorText = ""
                } else {
                    view?.setError("Incorrect password!!")
                    currentResult = false
                    errorText = "Incorrect password"
                }
            }
        }.start()
    }

    override fun onCredentialsChanged() {
        //todo
    }


    private fun checkCredentials(login: String, password: String): Boolean {
        if(password == passwordSaved.toString()) {
            return true
        }
        return false
    }

}