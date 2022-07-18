package com.example.semob

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.car.R
import com.example.semob.room.Car
import kotlinx.android.synthetic.main.items_customer.view.*


class CustomerAdapter(
    private var cars: LiveData<List<Car>>,
    private val listener: OnAdapterListener
) : RecyclerView.Adapter<CustomerAdapter.CarViewHolder>() {
    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.items_customer, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars.value?.get(position)
        holder.itemView.apply {
            findViewById<TextView>(R.id.tv_customer).text =
                cars.value?.get(position)?.customer ?: ""
            findViewById<TextView>(R.id.tv_car).text = cars.value?.get(position)?.name ?: ""
            val total =
                (cars.value?.get(position)?.price!! * cars.value?.get(position)?.day!!).toString()
            findViewById<TextView>(R.id.tv_total).text = total
        }
        holder.itemView.btn_updatecustomer.setOnClickListener {
            if (car != null) {
                listener.onUpdate(car)
            }
        }
        holder.itemView.btn_deletecustomer.setOnClickListener {
            if (car != null) {
                listener.onDelete(car)
            }
        }
        holder.itemView.setOnClickListener {
            if (car != null) {
                listener.onDetail(car)
            }
        }
    }

    override fun getItemCount(): Int {
        return cars.value?.size ?: 0
    }

    interface OnAdapterListener {
        fun onDelete(car: Car)
        fun onUpdate(car: Car)
        fun onDetail(car: Car)
    }
}