package com.example.artapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object FragmentManager {
    var currentFrag: Fragment? = null

    @SuppressLint("CommitTransaction")
    fun setFragment (newFrag: Fragment, activity: AppCompatActivity, bundle: Bundle? = null) {
        /*val transaction = */activity.supportFragmentManager.beginTransaction()
            .replace(R.id.frameLatout, newFrag.apply { arguments = bundle })
            .addToBackStack(null)
            .commit()
        currentFrag = newFrag
    }
}