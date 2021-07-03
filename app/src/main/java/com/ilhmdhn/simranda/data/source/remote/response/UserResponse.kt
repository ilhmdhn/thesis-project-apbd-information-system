package com.ilhmdhn.simranda.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UserResponse(

    @field:SerializedName("data")
    val data: List<DataUserItem>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Boolean
)

@Parcelize
data class DataUserItem(

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("fullname")
    val fullname: String,

    @field:SerializedName("level")
    val level: String,

    @field:SerializedName("passowrd")
    val password: String? = null

) : Parcelable

data class createUserResponse(
    @field:SerializedName("status")
    val status: Boolean
)

data class updateUserResponse(
    @field:SerializedName("status")
    val status: Boolean,

    )

data class deleteUserResponse(
    @field:SerializedName("status")
    val status: Boolean
)
