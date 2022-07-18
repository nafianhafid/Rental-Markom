package com.example.car

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.semob.room.Car
import com.example.semob.room.CarDB
import kotlinx.android.synthetic.main.activity_info_customer.*
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerInfoActivity : AppCompatActivity() {
    private val db by lazy { CarDB(this) }
    private var carId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_customer)
        carId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val car = db?.carDao()?.getCar(carId)[0]
            tv_customer_name.setText("Name : " + car.customer)
            tv_customer_phoneNumber.setText("Phone : " + car.phoneNumber)
            tv_customer_day.setText("Day : " + car.day)
            tv_customer_car.setText("Car : " + car.name)
            tv_customer_total.setText("Total : " + (car.day * car.price))
        }
        btn_backtolistcustomer.setOnClickListener {
            startActivity(Intent(this, CustomerActivity::class.java))
        }
    }
}