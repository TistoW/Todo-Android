package com.tisto.todo.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.inyongtisto.myhelper.extension.toastSimple
import java.net.URLEncoder
import java.text.SimpleDateFormat

fun String?.defultError(): String {
    return this ?: Constant.DEFAULT_ERROR
}

fun Activity.setStatusBarBackgroudColor(color: Int) {
    val window: Window = window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = getColor(color)
}

fun String.searchQuery(): String {
    return "%$this%"
}

fun Activity.openWhatsApp(nomor: String, message: String) {
    try {
        val packageManager: PackageManager = packageManager
        val i = Intent(Intent.ACTION_VIEW)
        val url = "https://api.whatsapp.com/send?phone=" + nomor + "&text=" + URLEncoder.encode(message, "UTF-8")
        i.setPackage("com.whatsapp")
        i.data = Uri.parse(url)
        if (i.resolveActivity(packageManager) != null) {
            startActivity(i)
        } else {
            openWhatsappAlternate(nomor, message)
        }
    } catch (e: Exception) {
        openWhatsappAlternate(nomor, message)
    }
}

fun Activity.openWhatsappAlternate(nomor: String, message: String) {
    try {
        var toNumber = nomor // contains spaces.
        toNumber = toNumber.replace("+", "").replace(" ", "")
        val sendIntent = Intent("android.intent.action.MAIN")
        sendIntent.putExtra("jid", "$toNumber@s.whatsapp.net")
        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.setPackage("com.whatsapp")
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    } catch (e: Exception) {
        toastSimple("Gagal Membuka Whatsapp!")
    }
}


@SuppressLint("SimpleDateFormat")
fun String.convertTanggal(
    formatBaru: String,
    fromatLama: String = "yyyy-MM-dd kk:mm:ss"
): String {
    val dateFormat = SimpleDateFormat(fromatLama)
    val confert = dateFormat.parse(this)
    dateFormat.applyPattern(formatBaru)
    return dateFormat.format(confert ?: "")
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}