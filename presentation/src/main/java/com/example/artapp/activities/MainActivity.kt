package com.example.artapp.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.artapp.R
import com.example.artapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var defPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        val currentTheme = getSelectedTheme()
        setTheme(currentTheme)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED
    }

    private fun getSelectedTheme(): Int {
        return if (defPref.getString("theme_key", "light") == "light") {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            R.style.Theme_ArtApp_Light
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            R.style.Theme_ArtApp_Dark
        }
    }
}


//val content: View = findViewById(android.R.id.content)
//content.viewTreeObserver.addOnPreDrawListener(
//object : ViewTreeObserver.OnPreDrawListener {
//    override fun onPreDraw(): Boolean {
//        return if (viewModel.liveState.value is States.Data) {
//            content.viewTreeObserver.removeOnPreDrawListener(this)
//            true
//        } else {
//            false
//        }
//    }
//}
//)