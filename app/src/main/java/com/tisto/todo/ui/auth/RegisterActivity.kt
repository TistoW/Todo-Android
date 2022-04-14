package com.tisto.todo.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import com.inyongtisto.myhelper.extension.*
import com.tisto.todo.NavigationActivity
import com.tisto.todo.core.data.source.remote.network.State
import com.tisto.todo.core.data.source.remote.request.RegisterRequest
import com.tisto.todo.databinding.ActivityRegisterBinding
import com.tisto.todo.ui.base.BaseActivity
import com.tisto.todo.util.defultError
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity() {

    private val viewModel: AuthViewModel by viewModel()

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        mainButton()
    }

    private fun init() {
        // setup layout
    }

    private fun validate(): Boolean {
        if (binding.edtName.isEmpty()) return false
        if (binding.edtEmail.isEmpty()) return false
        if (binding.edtPassword.isEmpty()) return false
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun mainButton() {
        binding.btnRegister.setOnClickListener {
            if (validate()) register()
        }

        binding.btnDummy.setOnLongClickListener {
            binding.apply {
                edtEmail.setText("tisto")
                edtEmail.setText("tisto@gmail.com")
                edtPassword.setText("admin123")
            }
            return@setOnLongClickListener true
        }
    }

    private fun register() {
        val reqBody = RegisterRequest(
                binding.edtName.getString(),
                binding.edtEmail.getString(),
                binding.edtPassword.getString()
        )

        viewModel.register(reqBody).observe(this) {
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