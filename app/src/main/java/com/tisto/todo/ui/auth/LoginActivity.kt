package com.tisto.todo.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import com.inyongtisto.myhelper.extension.*
import com.tisto.todo.NavigationActivity
import com.tisto.todo.core.data.source.remote.network.State
import com.tisto.todo.core.data.source.remote.request.LoginRequest
import com.tisto.todo.databinding.ActivityLoginBinding
import com.tisto.todo.ui.base.BaseActivity
import com.tisto.todo.util.defultError
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private val viewModel: AuthViewModel by viewModel()

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        mainButton()
    }

    private fun init() {
        // setup layout
    }

    private fun validate(): Boolean {
        if (binding.edtEmail.isEmpty()) return false
        if (binding.edtPassword.isEmpty()) return false
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun mainButton() {
        binding.btnLogin.setOnClickListener {
            if (validate()) login()
        }

        binding.btnDummy.setOnLongClickListener {
            binding.apply {
                edtEmail.setText("tisto@gmail.com")
                edtPassword.setText("admin123")
            }
            return@setOnLongClickListener true
        }

        binding.btnDaftar.setOnClickListener {
            intentActivity(RegisterActivity::class.java)
        }
    }

    private fun login() {
        val reqBody = LoginRequest(
                binding.edtEmail.getString(),
                binding.edtPassword.getString()
        )

        viewModel.login(reqBody).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    val user = it.body
                    progress.dismiss()
                    toastSuccess("Selamat Datang " + user?.name)
                    pushActivity(NavigationActivity::class.java)
                }
                State.ERROR -> {
                    progress.dismiss()
                    toastError(it.message.defultError())
                }
                State.LOADING -> {
                    progress.show()
                }
            }
        }
    }
}