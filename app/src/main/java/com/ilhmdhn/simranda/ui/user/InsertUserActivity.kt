package com.ilhmdhn.simranda.ui.user

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.databinding.ActivityInsertUserBinding
import com.ilhmdhn.simranda.viewmodel.ViewModelFactory

class InsertUserActivity : AppCompatActivity() {

    private var _activityInsertUser: ActivityInsertUserBinding? = null
    private val binding get() = _activityInsertUser
    private var userLevel: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityInsertUser = ActivityInsertUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        val sharedPref = applicationContext.getSharedPreferences(
            R.string.setting_data.toString(),
            Context.MODE_PRIVATE
        )
        val key = sharedPref?.getString("key", "").toString()

        binding?.btnSubmit?.setOnClickListener {

            val username = binding?.edtUserId?.text.toString()
            val fullname = binding?.edtFullname?.text.toString()
            val password = binding?.edtPassword?.text.toString()
            val verifPassword = binding?.edtVerifPassowrd?.text.toString()

            if (username.isNullOrEmpty()) {
                binding?.edtUserId?.error = "Kosong"
            } else if (fullname.isNullOrEmpty()) {
                binding?.edtFullname?.error = "Kosong"
            } else if (password.isNullOrEmpty()) {
                binding?.edtPassword?.error = "Kosong"
            } else if (verifPassword.isNullOrEmpty()) {
                binding?.edtVerifPassowrd?.error = "Kosong"
            } else if (password != verifPassword) {
                binding?.edtPassword?.error = "Beda"
                binding?.edtVerifPassowrd?.error = "Beda"
                Toast.makeText(this, "Password Berbeda", Toast.LENGTH_SHORT).show()
            } else if (userLevel == null) {
                binding?.radioGuest?.error = "Pilih Salah Satu"
                binding?.radioOperator?.error = "Pilih Salah Satu"
            } else {
                viewModel.insertUser(
                    key,
                    username,
                    fullname,
                    userLevel!!,
                    password,
                    applicationContext
                )
            }
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.radio_guest ->
                    if (checked) {
                        userLevel = "3"
                    }
                R.id.radio_operator ->
                    if (checked) {
                        userLevel = "2"
                    }
            }
        }
    }
}