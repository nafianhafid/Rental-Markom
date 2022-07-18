package com.example.car

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_listcar).setOnClickListener(this)
        findViewById<Button>(R.id.btn_listcustomer).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_listcar -> {
                val intent = Intent(this@MainActivity, CarActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_listcustomer -> {
                val intent = Intent(this@MainActivity, CustomerActivity::class.java)
                startActivity(intent)
            }
        }
    }
}