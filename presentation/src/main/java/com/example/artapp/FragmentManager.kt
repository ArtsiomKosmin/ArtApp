package com.example.artapp

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object FragmentManager {
    var currentFrag: Fragment? = null

    fun setFragment (newFrag: Fragment, activity: AppCompatActivity) {
        /*val transaction = */activity.supportFragmentManager.beginTransaction()
            .replace(R.id.frameLatout, newFrag)
            .commit()
        currentFrag = newFrag
    }
}