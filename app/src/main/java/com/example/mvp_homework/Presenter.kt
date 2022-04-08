package com.example.mvp_homework

import android.os.Handler
import android.os.Looper

class Presenter: Contract.Presenter {

    private var view: Contract.View? = null
    private val uiHandler = Handler(Looper.getMainLooper())
    private var currentResult: Boolean = false
    private var errorText: String = ""

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
                    view?.setError("Неверный пароль!!")
                    currentResult = false
                    errorText = "Неверный пароль"
                }
            }

        }.start()
    }

    override fun onCredentialsChanged() {
        //todo
    }

    override fun onBackPassword() {
        TODO("Not yet implemented")
    }

    override fun onRegistration() {
        TODO("Not yet implemented")
    }

    private fun checkCredentials(login: String, password: String): Boolean {
        return login == password
    }

}