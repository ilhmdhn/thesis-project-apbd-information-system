package com.ilhmdhn.simranda.ui.user

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilhmdhn.simranda.data.source.remote.ApiResponse
import com.ilhmdhn.simranda.data.source.remote.response.*
import com.ilhmdhn.simranda.utils.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private val _resultUser = MutableLiveData<List<DataUserItem>>()
    val resultUser: LiveData<List<DataUserItem>> = _resultUser

    companion object {
        private const val TAG = "DataSource"
    }

    fun getUser(key: String, context: Context) {
        val client = ApiConfig.getApiService().getUser(key)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Berhasil", Toast.LENGTH_LONG).show()
                    _resultUser.value = response.body()?.data
                } else {
                    Toast.makeText(context, "Gagal", Toast.LENGTH_LONG).show()
                    ApiResponse.empty(response.message(), response.body())
                    Log.e(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                ApiResponse.error("${t.message}", null)
                Log.e(TAG, "${t.message}")
            }
        })
    }

    fun updateUser(
        key: String,
        username: String,
        fullname: String,
        level: String,
        password: String,
        context: Context
    ) {
        val client = ApiConfig.getApiService().updateUser(username, key, fullname, level, password)
        client.enqueue(object : Callback<updateUserResponse> {
            override fun onResponse(
                call: Call<updateUserResponse>,
                response: Response<updateUserResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "succes", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "server return error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<updateUserResponse>, t: Throwable) {
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun deleteUser(key: String, username: String, context: Context) {
        val client = ApiConfig.getApiService().deleteUser(username, key)
        client.enqueue(object : Callback<deleteUserResponse> {
            override fun onResponse(
                call: Call<deleteUserResponse>,
                response: Response<deleteUserResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "succes", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "server return error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<deleteUserResponse>, t: Throwable) {
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun insertUser(
        key: String,
        username: String,
        fullname: String,
        level: String,
        password: String,
        context: Context
    ) {
        val client = ApiConfig.getApiService().createUser(username, fullname, level, password, key)
        client.enqueue(object : Callback<createUserResponse> {
            override fun onResponse(
                call: Call<createUserResponse>,
                response: Response<createUserResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "succes", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "server return error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<createUserResponse>, t: Throwable) {
                Toast.makeText(context, "onFailure: ${t.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }
}