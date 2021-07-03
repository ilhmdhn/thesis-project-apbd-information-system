package com.ilhmdhn.simranda.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ilhmdhn.simranda.R
import com.ilhmdhn.simranda.data.source.remote.response.LoginResponse
import com.ilhmdhn.simranda.databinding.ActivityLoginBinding
import com.ilhmdhn.simranda.ui.home.MainActivity
import com.ilhmdhn.simranda.utils.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private var _activityLoginBinding: ActivityLoginBinding? = null
    private val binding get() = _activityLoginBinding

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val sharedPref =
            this.getSharedPreferences(R.string.setting_data.toString(), Context.MODE_PRIVATE)

        val loginStatus: Boolean? = sharedPref?.getBoolean("login", false)
        val key: String = sharedPref?.getString("key", getString(R.string.default_key)).toString()

        if (loginStatus == true) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        supportActionBar?.hide()

        binding?.btnLogin?.setOnClickListener {
            if (binding?.edtUsername?.text.isNullOrEmpty()) {
                binding?.edtUsername?.error = "Kosong"
            } else if (binding?.edtPassowrd?.text.isNullOrEmpty()) {
                binding?.edtPassowrd?.error = "Kosong"
            } else {
                val editor = sharedPref.edit()
                val client = ApiConfig.getApiService().login(
                    key,
                    binding?.edtUsername?.text.toString(),
                    binding?.edtPassowrd?.text.toString()
                )
                client.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            editor.putString("user", response.body()?.data?.username)
                            editor.putBoolean("login", true)
                            editor.putString("fullname", response.body()?.data?.fullname)
                            editor.putString("level", response.body()?.data?.level)
                            editor.putString("key", response.body()?.data?.key)
                            editor?.apply()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Login gagal, periksa username dan passowrd ${
                                    response.body().toString()
                                }",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(
                            this@LoginActivity,
                            "onFailure: ${t.message.toString()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }
    }
}