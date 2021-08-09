package com.example.myapplication.constants

object NetworkConstants {
    const val BASE_URL = "https://hello-world.innocv.com/api/"
    const val REPLACE_KEY = "{id}"
    const val GET_USERS = "User"
    const val USER_ID = "id"
    const val USER_MORE_ID = "$GET_USERS/$REPLACE_KEY"
    const val MEDIATYPE_PARSE = "application/json; charset=utf-8"
}