package com.example.mvp_homework

import androidx.annotation.MainThread

class Contract {

    interface View {
        @MainThread
        fun setError(error: String)
        @MainThread
        fun showProgress()
        @MainThread
        fun hideProgress()
        @MainThread
        fun setSuccess()
        @MainThread
        fun onBackPassword()
        @MainThread
        fun onRegistration()
    }

    interface Presenter {
        fun onAttach(view: View)
        fun onLogin(login: String, password: String)
        fun onCredentialsChanged()


    }

}