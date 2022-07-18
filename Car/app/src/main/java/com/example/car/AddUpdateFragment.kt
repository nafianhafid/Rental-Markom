package com.example.car

import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.semob.room.Car
import com.example.semob.room.CarDB
import kotlinx.android.synthetic.main.fragment_add_update.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddUpdateFragment : Fragment(R.layout.fragment_add_update) {
    private val db by lazy { getContext()?.let { CarDB(it) } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var displayView = inflater.inflate(R.layout.fragment_add_update, container, false)
        // Add Car
        displayView.findViewById<Button>(R.id.btn_submit).setOnClickListener {
            // mengambil data teko edit text
            val name: String = displayView.findViewById<EditText>(R.id.et_name).text.toString()
            val category: String =
                displayView.findViewById<EditText>(R.id.et_category).text.toString()
            val price: String = displayView.findViewById<EditText>(R.id.et_price).text.toString()
            //seleksi form kosong
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(category) && !TextUtils.isEmpty(price)) {
                CoroutineScope(Dispatchers.IO).launch {
                    db?.carDao()?.addCar(
                        Car(0, name, category, price.toInt(), 0, "nothing", "nothing", 0)
                    )
                }
//                mengosongkan edit text
                displayView.findViewById<EditText>(R.id.et_name).setText("")
                displayView.findViewById<EditText>(R.id.et_category).setText("")
                displayView.findViewById<EditText>(R.id.et_price).setText("")

                Toast.makeText(getActivity(), "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                // pindah fragment list car
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                if (transaction != null) {
                    transaction.replace(R.id.fragment_car, CarListFragment())
                    transaction.disallowAddToBackStack()
                    transaction.commit()
                }
            } else {
//                data kosng
                Toast.makeText(getActivity(), "Data Belum Lengkap", Toast.LENGTH_SHORT).show()
            }
        }

        // Backtolistcar
        displayView.findViewById<Button>(R.id.btn_backtolist).setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            if (transaction != null) {
                transaction.replace(R.id.fragment_car, CarListFragment())
                transaction.disallowAddToBackStack()
                transaction.commit()
            }
        }
        return displayView
    }
}