package com.example.contactespdm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.contactespdm.databinding.ActivityContactBinding
import com.example.contactespdm.model.Contact
import com.google.gson.Gson

class ContactActivity : AppCompatActivity() {
    private val acb: ActivityContactBinding by lazy{
        ActivityContactBinding.inflate(layoutInflater)
    }
    val gson:Gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)


        acb.saveBt.setOnClickListener {
            val name = acb.nameEt.text.toString()
            val phone = acb.phoneEt.text.toString()
            val email = acb.emailEt.text.toString()
            val address = acb.addressEt.text.toString()
            val new_contact = Contact(
               name=name,
                phone = phone,
                email = email,
                address = address
            )
            val result_intent = Intent()
            result_intent.putExtra("new_contact",gson.toJson(new_contact))

            setResult(RESULT_OK,result_intent)
            finish()

        }
    }
    private fun onEditCell(){}
}