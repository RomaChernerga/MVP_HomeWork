package com.example.mvp_homework

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.mvp_homework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Contract.View {

    private lateinit var binding: ActivityMainBinding
    private var presenter: Contract.Presenter? = null
    var savedPassword: String? = null

    private fun getPassword() {
        savedPassword = MyDialogFragment.MyPreferencesUserValue(context = this@MainActivity).userValue.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = restorePresenter()
        presenter?.onAttach(this)

        getPassword()

        //Кнопка принятия пароля и логина
        binding.btnEnter.setOnClickListener {
                // презентер должен принимать onLogin, который в скою очередь принимает логин и пароль
            presenter?.onLogin(
                binding.editTextLogin.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }

        binding.textViewBackPass.isVisible = false

        //Кнопка регистрации нового пароля
        binding.btnReg.setOnClickListener {
            val myDialogFragment = MyDialogFragment()
            val  manager = supportFragmentManager
            myDialogFragment.show(manager, "myDialog")
        }

        //Кнопка восстановления пароля
        binding.btnBackPassword.setOnClickListener {
            binding.apply {
                textViewBackPass.isVisible = true
                textViewBackPass.setBackgroundColor(Color.GREEN)
                binding.textViewBackPass.text = MyDialogFragment.MyPreferencesUserValue(context = this@MainActivity).userValue.toString()
            }
        }

    }

    @SuppressLint("SetTextI18n")
    @MainThread
    override fun setError(error: String) {
        binding.apply {
            textViewBackPass.setText("$error").toString()
            textViewBackPass.isVisible = true
            textViewBackPass.setBackgroundColor(Color.RED)
        }
    }

    override fun showProgress() {
        @MainThread
        binding.btnEnter.isEnabled = false
    }

    @MainThread
    override fun hideProgress() {

        binding.btnEnter.isEnabled = true
    }

    @MainThread
    override fun setSuccess() {
        binding.apply {
            textViewBackPass.isVisible = true
            textViewBackPass.setBackgroundColor(Color.GREEN)
            textViewBackPass.setText(getString(R.string.whenLogAndPasCorrect)).toString()
        }
    }

    @MainThread
    override fun onBackPassword() {
        //todo
    }

    @MainThread
    override fun onRegistration() {
        //todo
    }

    @MainThread
    // Метод чтобы положить объект
    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }

    @MainThread
    private fun restorePresenter(): Presenter {
        val presenter =  lastCustomNonConfigurationInstance as? Presenter
        return presenter ?: Presenter()
    }

}