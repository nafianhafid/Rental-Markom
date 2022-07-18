package com.example.car

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.semob.room.Car
import com.example.semob.room.CarDB
import kotlinx.android.synthetic.main.activity_update_customer.*
import kotlinx.android.synthetic.main.activity_update_customer.tv_car
import kotlinx.android.synthetic.main.items_customer.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditCustomerActivity: AppCompatActivity() {
    private val db by lazy { CarDB(this) }
    private var carId: Int = 0
    private lateinit var car: Car
    private var set: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_customer)
        carId = intent.getIntExtra("intent_id", 0)
        set = intent.getIntExtra("set", 0)
        CoroutineScope(Dispatchers.IO).launch {
            car = db?.carDao()?.getCar(carId)[0]
            tv_car.setText(car.name)
            if (set==1){
                et_customer.setText(car.customer)
                et_phoneNumber.setText(car.phoneNumber.toString())
                et_day.setText(car.day).toString()
            }
        }

        if (!TextUtils.isEmpty(et_customer.text.toString()) && !TextUtils.isEmpty(et_phoneNumber.text.toString()) && !TextUtils.isEmpty(et_day.text.toString())) {
            btn_edit_customer.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    db?.carDao()?.updateCar(
                        Car(
                            carId,
                            car.name,
                            car.category,
                            car.price,
                            1,
                            et_customer.text.toString(),
                            et_phoneNumber.text.toString(),
                            et_day.text.toString().toInt()
                        )
                    )
                }
                startActivity(Intent(this, CustomerActivity::class.java))
            }
        } else {
            Toast.makeText(this, "Data Belum Lengkap", Toast.LENGTH_SHORT).show()
        }
    }
}