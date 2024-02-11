package com.example.artapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.artapp.FragmentManager
import com.example.artapp.R
import com.example.artapp.databinding.ActivityMainBinding
import com.example.artapp.favorite.FavouriteFragment
import com.example.artapp.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            showHomeFragment()
        }
        setBottomNavListener()
    }

    private fun setBottomNavListener() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> FragmentManager.setFragment(HomeFragment.getInstance(), this)
                R.id.favorite -> FragmentManager.setFragment(FavouriteFragment.newInstance(), this)
                R.id.settings -> Log.d("Check", "settings")
            }
            true
        }
    }

    private fun showHomeFragment() {
        FragmentManager.setFragment(HomeFragment.getInstance(), this)
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