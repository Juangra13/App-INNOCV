package com.example.myapplication.business.model
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("birthdate")
    val birthdate: String? = null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String? = null
)