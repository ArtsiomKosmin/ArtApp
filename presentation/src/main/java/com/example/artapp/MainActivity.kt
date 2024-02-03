package com.example.artapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.artapp.databinding.ActivityMainBinding
import com.example.artapp.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavListner()

        if (savedInstanceState == null) {
            FragmentManager.setFragment(HomeFragment.getInstance(), this)
        }
    }

    private fun setBottomNavListner() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    FragmentManager.setFragment(HomeFragment.getInstance(), this)
                }
                R.id.favorite -> {
                    Log.d("Check", "favorite")
                }
                R.id.settings -> {
                    Log.d("Check", "settings")
                }
            }
            true
        }
    }


}