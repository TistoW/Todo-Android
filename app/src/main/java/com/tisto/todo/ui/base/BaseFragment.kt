package com.tisto.todo.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.inyongtisto.myhelper.extension.toastError
import com.inyongtisto.myhelper.util.AppProgressDialog

abstract class BaseFragment : Fragment() {
    lateinit var progress: AppProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupProgress()
    }
    private fun setupProgress() {
        progress = AppProgressDialog(requireActivity())
        progress.setCancelable(false)
        progress.setCanceledOnTouchOutside(false)
    }

}