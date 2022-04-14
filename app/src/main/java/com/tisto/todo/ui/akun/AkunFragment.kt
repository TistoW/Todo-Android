package com.tisto.todo.ui.akun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inyongtisto.myhelper.extension.pushActivity
import com.tisto.todo.databinding.FragmentAkunBinding
import com.tisto.todo.ui.splash.SplashScreenActivity
import com.tisto.todo.util.Prefs

class AkunFragment : Fragment() {

    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)

        mainButton()
        return binding.root
    }

    private fun mainButton() {
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        Prefs.clear()
        pushActivity(SplashScreenActivity::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}