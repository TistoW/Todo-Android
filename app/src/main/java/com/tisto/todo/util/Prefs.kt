package com.tisto.todo.util

import com.chibatching.kotpref.KotprefModel
import com.inyongtisto.myhelper.extension.toJson
import com.inyongtisto.myhelper.extension.toModel
import com.tisto.todo.core.data.source.remote.model.User

object Prefs : KotprefModel() {

    var isLogin by booleanPref(false)
    var token by nullableStringPref()
    var user by nullableStringPref()
    var whatsapp by stringPref(Constant.whatsapp)

}

fun setUser(user: User?) {
    Prefs.user = user?.toJson()
}

fun getUser() = Prefs.user?.toModel(User::class.java)
fun getToken() = getUser()?.token ?: "token"
fun getUserId() = getUser()?.id