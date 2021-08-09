package com.example.myapplication.business.model
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("birthdate")
    var birthdate: String? = null,
    @SerializedName("id")
    val id: Int

)