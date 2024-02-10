package com.example.artapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object FragmentManager {
    fun setFragment(newFrag: Fragment, activity: AppCompatActivity, bundle: Bundle? = null) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        bundle?.let { newFrag.arguments = it }
        transaction.replace(R.id.frameLatout, newFrag)
        transaction.addToBackStack(newFrag::class.java.name)
        transaction.commit()
    }
    fun popBackStack(activity: AppCompatActivity) {
        activity.supportFragmentManager.popBackStack()
    }
}
