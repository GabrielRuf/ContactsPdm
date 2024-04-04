package com.example.contactespdm.ui

import android.app.Activity
import android.content.Intent
import com.example.contactespdm.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import com.example.contactespdm.ContactAdapter
import com.example.contactespdm.databinding.ActivityMainBinding
import com.example.contactespdm.model.Contact
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private val amb : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val contactActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val newContact = data?.getStringExtra("new_contact")
            Log.d("Novo contato", newContact.toString())
            if ( newContact != null){
                contactList.add(gson.fromJson(newContact.toString(),Contact::class.java))
                contactAdapter.notifyDataSetChanged()
            }
        }
    }

    val gson: Gson = Gson()

    // Data Source
    private val contactList: MutableList<Contact> = mutableListOf()

    // Adapter
    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(this, contactList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        amb.toolbarIn.apply {
            setSupportActionBar(this.toolbar)
        }

        fillContacts()

        amb.contactesLv.adapter = contactAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.contactItemMenu -> {
                val intent = Intent(this, ContactActivity::class.java)
                contactActivityResultLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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