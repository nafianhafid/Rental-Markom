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
import com.example.semob.CarRepo
import com.example.semob.CarViewModel
import com.example.semob.CustomerAdapter
import com.example.semob.room.Car
import com.example.semob.room.CarDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerListFragment : Fragment(R.layout.fragment_customerlist) {
    private val db by lazy { getContext()?.let { CarDB(it) } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var displayView = inflater.inflate(R.layout.fragment_customerlist, container, false)
        val carVM = CarViewModel(carRepo = CarRepo())
        val rvCar = displayView.findViewById<RecyclerView>(R.id.rv_customer)
        // Read Car
        CoroutineScope(Dispatchers.IO).launch {
            val cars = db?.carDao()?.getCustomers()
            if (cars != null) {
                carVM.addCar(cars)
            }
        }

        val adapter = CustomerAdapter(carVM.getCars(), object : CustomerAdapter.OnAdapterListener {
            override fun onDelete(car: Car) {
                val dialog = AlertDialog.Builder(getContext())
                dialog.apply {
                    setTitle("Konfirmasi Selesai")
                    setMessage("Yakin transaksi ${car.name} telah selesai?")
                    setNegativeButton("Batal") { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }
                    setPositiveButton("Selesai") { dialogInterface, i ->
                        CoroutineScope(Dispatchers.IO).launch {
                            db?.carDao()?.updateCar(
                                Car(
                                    car.id,
                                    car.name,
                                    car.category,
                                    car.price,
                                    0,
                                    "nothing",
                                    "nothing",
                                    0
                                )
                            )
                            val transaction = activity?.supportFragmentManager?.beginTransaction()
                            if (transaction != null) {
                                transaction.replace(R.id.fragment_customer, CustomerListFragment())
                                transaction.disallowAddToBackStack()
                                transaction.commit()
                            }
                            dialogInterface.dismiss()
                        }
                    }
                }
                dialog.show()
            }

            override fun onUpdate(car: Car) {
                CoroutineScope(Dispatchers.IO).launch {
                    startActivity(
                        Intent(activity, EditCustomerActivity::class.java).putExtra(
                            "intent_id",
                            car.id
                        ).putExtra("set",1)
                    )
                }
            }
        })

        rvCar.adapter = adapter
        rvCar.layoutManager = LinearLayoutManager(getContext())

        //
        displayView.findViewById<Button>(R.id.btn_addcustomer).setOnClickListener {
            startActivity(
                Intent(activity, CarActivity::class.java)
            )
        }
        return displayView
    }
}