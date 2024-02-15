package com.example.artapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.artapp.R
import com.example.artapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
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