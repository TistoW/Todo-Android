package com.tisto.todo.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.inyongtisto.myhelper.util.AppProgressDialog

abstract class BaseActivity : AppCompatActivity() {
    lateinit var progress: AppProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupProgress()
    }

    private fun setupProgress() {
        progress = AppProgressDialog(this)
        progress.setCancelable(false)
        progress.setCanceledOnTouchOutside(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}