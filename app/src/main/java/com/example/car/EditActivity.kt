package com.example.car

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.semob.room.Car
import com.example.semob.room.CarDB
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity: AppCompatActivity() {
    private val db by lazy { CarDB(this) }
    private var carId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        carId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val car = db?.carDao()?.getCar(carId)[0]
            et_name.setText(car.name)
            et_category.setText(car.category)
            et_price.setText(car.price.toString())
        }

        if (!TextUtils.isEmpty(et_name.text.toString()) && !TextUtils.isEmpty(et_category.text.toString()) && !TextUtils.isEmpty(et_price.text.toString())) {
            btn_edit.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    db?.carDao()?.updateCar(
                        Car(
                            carId,
                            et_name.text.toString(),
                            et_category.text.toString(),
                            et_price.text.toString().toInt(),
                            0,
                            "nothing",
                            "nothing",
                            0
                        )
                    )
                }
                startActivity(Intent(this, CarActivity::class.java))
            }
        } else {
            Toast.makeText(this, "Data Belum Lengkap", Toast.LENGTH_SHORT).show()
        }
    }
}