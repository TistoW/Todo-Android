package com.tisto.todo

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.tisto.todo.databinding.ActivityNavigationBinding
import com.tisto.todo.ui.akun.AkunFragment
import com.tisto.todo.ui.retrofit.RetrofitFragment
import com.tisto.todo.ui.room.RoomFragment

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding
    private val fragmentRetrofit: Fragment = RetrofitFragment()
    private val fragmentRoom: Fragment = RoomFragment()
    private val fragmentAkun: Fragment = AkunFragment()
    private val fm: FragmentManager = supportFragmentManager
    private var active: Fragment = fragmentRetrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNav()
    }

    private fun setupNav() {
        fm.beginTransaction().add(R.id.nav_host_fragment_activity_navigation, fragmentRetrofit).show(fragmentRetrofit).commit()
        fm.beginTransaction().add(R.id.nav_host_fragment_activity_navigation, fragmentRoom).hide(fragmentRoom).commit()
        fm.beginTransaction().add(R.id.nav_host_fragment_activity_navigation, fragmentAkun).hide(fragmentAkun).commit()

        val navView: BottomNavigationView = binding.navView
        navView.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.navigation_home -> {
                    callFragment(fragmentRetrofit)
                }
                R.id.navigation_dashboard -> {
                    callFragment(fragmentRoom)
                }
                R.id.navigation_notifications -> {
                    callFragment(fragmentAkun)
                }
            }

            return@setOnItemSelectedListener true
        }
    }

    private fun callFragment(fragment: Fragment) {
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }
}