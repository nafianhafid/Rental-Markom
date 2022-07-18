package com.example.car

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.semob.room.CarDB

class CustomerActivity : AppCompatActivity() {
    private var fragList = CustomerListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        // Set First Fragment
        fragmentTransaction.replace(R.id.fragment_customer, fragList)
        fragmentTransaction.commit()
    }
}