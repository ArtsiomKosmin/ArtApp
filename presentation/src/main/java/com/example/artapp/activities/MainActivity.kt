package com.example.artapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.artapp.R
import com.example.artapp.databinding.ActivityMainBinding
import com.example.artapp.viewBinding
import com.example.domain.useCase.GetSharedPrefsUseCase
import com.google.android.material.navigation.NavigationBarView
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    @Inject
    lateinit var sharedPrefsUseCase: GetSharedPrefsUseCase
    private val sharedPrefs by lazy { sharedPrefsUseCase.getSharedPrefs() }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MainApp).appComponent.injectMainActivity(this)
        val currentTheme = getSelectedTheme()
        setTheme(currentTheme)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED
    }

    private fun getSelectedTheme(): Int {
        return if (sharedPrefs.getString("theme_key", "light") == "light") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            R.style.Theme_ArtApp_Light
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            R.style.Theme_ArtApp_Dark
        }
    }
}