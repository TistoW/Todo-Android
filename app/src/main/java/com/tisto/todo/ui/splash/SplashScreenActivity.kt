package com.tisto.todo.ui.splash

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.inyongtisto.myhelper.extension.*
import com.tisto.todo.BuildConfig
import com.tisto.todo.MainActivity
import com.tisto.todo.NavigationActivity
import com.tisto.todo.databinding.ActivitySplashBinding
import com.tisto.todo.ui.auth.LoginActivity
import com.tisto.todo.ui.base.BaseActivity
import com.tisto.todo.util.Prefs
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen, SetTextI18n")
class SplashScreenActivity : BaseActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        startActivityMainDelay()
    }


    private fun init() {
        binding.tvVersion.text = "v" + BuildConfig.VERSION_NAME
    }

    private fun startActivityMainDelay() {
        Handler(Looper.myLooper()!!).postDelayed({
            if (Prefs.isLogin) {
                pushActivity(NavigationActivity::class.java)
            } else {
                pushActivity(LoginActivity::class.java)
            }
        }, 500)
    }

}