package com.example.contactespdm.ui

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.contactespdm.databinding.ActivityMainBinding
import com.example.contactespdm.model.Contact

class MainActivity : AppCompatActivity() {
    private val amb : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data Source
    private val contactList: MutableList<Contact> = mutableListOf()

    // Adapter
    private val contactAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter(
            this,
            R.layout.simple_list_item_1,
            contactList.map{ contact ->
            contact.toString()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        fillContacts()

        amb.contactesLv.adapter = contactAdapter
    }

    private fun fillContacts(){
        for (i in 1 .. 50){
            contactList.add(
                Contact(
                    id=i,
                    name="Name $i",
                    address="Address $i",
                    phone="Phone $i",
                    email="Email $i"
                )
            )
        }
    }
}