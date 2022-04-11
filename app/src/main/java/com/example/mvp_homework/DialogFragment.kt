package com.example.mvp_homework

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment


class MyDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val view: View =
            layoutInflater.inflate(R.layout.dialog_fragment, null)

            val editNewPas = view.findViewById<EditText>(R.id.edit_new_pass)

        return androidx.appcompat.app.AlertDialog.Builder(requireActivity())
            .setMessage("Enter new password:")
            .setView(view)
//            .setTitle("Title")
            .setPositiveButton("OK"
            ) { dialog: DialogInterface?, which: Int ->
                MyPreferencesUserValue(requireActivity()).userValue = editNewPas.text.toString()
                Toast.makeText(context, "Новый пароль ${editNewPas.text} сохранен", Toast.LENGTH_SHORT).show()

            }
            .setNegativeButton("Cancel"
            ) { dialog: DialogInterface?, which: Int ->
                Toast.makeText(requireActivity(), "Отмена", Toast.LENGTH_SHORT).show()
            }
            .create()
    }
    class MyPreferencesUserValue(context: Context) {
        companion object{
            private const val USER_VALUE = "USER_VALUE"
        }
        private val preferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }
        var userValue = preferences.getString(USER_VALUE, "")
        set(value) = preferences.edit().putString(USER_VALUE, value).apply()
    }

}

