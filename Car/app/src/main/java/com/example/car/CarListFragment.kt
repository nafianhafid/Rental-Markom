package com.example.car

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semob.CarAdapter
import com.example.semob.CarRepo
import com.example.semob.CarViewModel
import com.example.semob.room.Car
import com.example.semob.room.CarDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarListFragment : Fragment(R.layout.fragment_carlist) {
    private val db by lazy { getContext()?.let { CarDB(it) } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var displayView = inflater.inflate(R.layout.fragment_carlist, container, false)
        val btn = displayView.findViewById<Button>(R.id.btn_addcar);
        val carVM = CarViewModel(carRepo = CarRepo())
        val rvCar = displayView.findViewById<RecyclerView>(R.id.rv_car)
        // Read Car
        CoroutineScope(Dispatchers.IO).launch {
            val cars = db?.carDao()?.getCars()
            if (cars != null) {
                carVM.addCar(cars)
            }
        }

        val adapter = CarAdapter(carVM.getCars(), object : CarAdapter.OnAdapterListener {
            override fun onClick(car: Car) {
                startActivity(
                    Intent(activity, EditActivity::class.java).putExtra("intent_id", car.id)
                )
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                if (transaction != null) {
                    transaction.replace(R.id.fragment_car, CarListFragment())
                    transaction.disallowAddToBackStack()
                    transaction.commit()
                }
            }

            override fun onDelete(car: Car) {
                val dialog = AlertDialog.Builder(getContext())
                dialog.apply {
                    setTitle("Konfirmasi Hapus")
                    setMessage("Yakin hapus ${car.name}?")
                    setNegativeButton("Batal") { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }
                    setPositiveButton("Hapus") { dialogInterface, i ->
                        CoroutineScope(Dispatchers.IO).launch {
                            db?.carDao()?.deleteCar(car)
                            val transaction = activity?.supportFragmentManager?.beginTransaction()
                            if (transaction != null) {
                                transaction.replace(R.id.fragment_car, CarListFragment())
                                transaction.disallowAddToBackStack()
                                transaction.commit()
                            }
                            dialogInterface.dismiss()
                        }
                    }
                }
                dialog.show()
            }

            override fun onBook(car: Car) {
                val dialog = AlertDialog.Builder(getContext())
                dialog.apply {
                    setTitle("Konfirmasi Sewa")
                    setMessage("Sewa ${car.name}?")
                    setNegativeButton("Batal") { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }
                    setPositiveButton("Ya") { dialogInterface, i ->
                        CoroutineScope(Dispatchers.IO).launch {
                            startActivity(
                                Intent(
                                    activity,
                                    EditCustomerActivity::class.java
                                ).putExtra("intent_id", car.id).putExtra("set", 0)
                            )
                            dialogInterface.dismiss()
                        }
                    }
                }
                dialog.show()
            }
        })

        rvCar.adapter = adapter
        rvCar.layoutManager = LinearLayoutManager(getContext())

        // pindah fragment
        displayView.findViewById<Button>(R.id.btn_addcar).setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            if (transaction != null) {
                transaction.replace(R.id.fragment_car, AddUpdateFragment())
                transaction.disallowAddToBackStack()
                transaction.commit()
            }
        }
        return displayView
    }
}