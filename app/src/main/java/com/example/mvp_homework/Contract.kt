package com.example.mvp_homework

class Contract {

    interface View {
        fun setError(error: String)
        fun showProgress()
        fun hideProgress()
        fun setSuccess()


    }

    interface Presenter {
        fun onAttach(view: View)
        fun onLogin(login: String, password: String)
        fun onCredentialsChanged()
        fun onBackPassword()
        fun onRegistration()

    }

}