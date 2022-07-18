package com.example.semob

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.car.R
import com.example.semob.room.Car
import kotlinx.android.synthetic.main.items.view.*


class CarAdapter(private var cars: LiveData<List<Car>>, private val listener: OnAdapterListener) :
    RecyclerView.Adapter<CarAdapter.CarViewHolder>() {
    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars.value?.get(position)
        holder.itemView.apply {
            findViewById<TextView>(R.id.tv_name).text = cars.value?.get(position)?.name ?: ""
            findViewById<TextView>(R.id.tv_category).text =
                cars.value?.get(position)?.category ?: ""
            findViewById<TextView>(R.id.tv_price).text =
                cars.value?.get(position)?.price.toString() ?: ""
        }
        // tkan tombol pensil
        holder.itemView.btn_updatecar.setOnClickListener {
            if (car != null) {
                listener.onClick(car)
            }
        }
        // tekan sampah
        holder.itemView.btn_deletecar.setOnClickListener {
            if (car != null) {
                listener.onDelete(car)
            }
        }
        // area items
        holder.itemView.setOnClickListener {
            if (car != null) {
                listener.onBook(car)
            }
        }
    }

    override fun getItemCount(): Int {
        return cars.value?.size ?: 0
    }

    interface OnAdapterListener {
        fun onClick(car: Car)
        fun onDelete(car: Car)
        fun onBook(car: Car)
    }
}