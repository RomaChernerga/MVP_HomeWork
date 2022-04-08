package com.example.mvp_homework

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
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

}