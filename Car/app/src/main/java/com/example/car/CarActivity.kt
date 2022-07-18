package com.example.car

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.semob.room.CarDB

class CarActivity : AppCompatActivity() {
    private val db by lazy { CarDB(this) }
    private var fragList = CarListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        // Set First Fragment
        fragmentTransaction.replace(R.id.fragment_car, fragList)
        fragmentTransaction.commit()
    }
}