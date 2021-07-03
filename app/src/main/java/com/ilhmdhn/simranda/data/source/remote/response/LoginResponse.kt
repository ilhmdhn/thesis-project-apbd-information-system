package com.ilhmdhn.simranda.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("data")
    val data: DataLogin,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Boolean
)

data class DataLogin(

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("fullname")
    val fullname: String,

    @field:SerializedName("level")
    val level: String,

    @field:SerializedName("key")
    val key: String
)
