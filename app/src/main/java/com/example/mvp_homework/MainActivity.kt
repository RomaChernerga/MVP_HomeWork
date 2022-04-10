package com.example.mvp_homework

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.mvp_homework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Contract.View {

    private lateinit var binding: ActivityMainBinding
    private var presenter: Contract.Presenter? = null



    override fun onCreate(savedInstanceState: Bundle?) {



        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = restorePresenter()
        presenter?.onAttach(this)

        binding.btnEnter.setOnClickListener {
                // презентер должен принимать onLogin, который в скою очередь принимает логин и пароль
            presenter?.onLogin(
                binding.editTextLogin.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }
        binding.textViewBackPass.isVisible = false

        binding.btnReg.setOnClickListener {
            val myDialogFragment = MyDialogFragment()
            val  manager = supportFragmentManager
            myDialogFragment.show(manager, "myDialog")
        }
    }



    @SuppressLint("SetTextI18n")
    override fun setError(error: String) {
        binding.apply {
            textViewBackPass.setText("$error").toString()
            textViewBackPass.isVisible = true
            textViewBackPass.setBackgroundColor(Color.RED)
        }
    }

    override fun showProgress() {
        binding.btnEnter.isEnabled = false
    }

    override fun hideProgress() {
        binding.btnEnter.isEnabled = true
    }

    override fun setSuccess() {
        binding.apply {
            textViewBackPass.isVisible = true
            textViewBackPass.setBackgroundColor(Color.GREEN)
            textViewBackPass.setText(getString(R.string.whenLogAndPasCorrect)).toString()
        }
    }


    // Метод чтобы взять объект
//    override fun getLastCustomNonConfigurationInstance(): Any? {
//        return super.getLastCustomNonConfigurationInstance()
//    }

    // Метод чтобы положить объект
    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }

    private fun restorePresenter(): Presenter {
        val presenter =  lastCustomNonConfigurationInstance as? Presenter
        return presenter ?: Presenter()
    }

//    class MyPreferencesUserValue(context: Context) {
//        companion object {
//            private const val USER_NAME = "USER_NAME"
//            private const val USER_PASS = "USER_PASS"
//        }
//
//        private val preferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }
//
//        var userName = preferences.getString(USER_NAME, "1234")
//        var userPass = preferences.getString(USER_PASS, "1234")
//
//        set(value) = preferences.edit().putString(USER_PASS, value).apply()
//
//    }

}