package com.example.contactespdm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contactespdm.Constantes.Name
import com.example.contactespdm.Constantes.Address
import com.example.contactespdm.Constantes.Phone
import com.example.contactespdm.Constantes.Email

import com.example.contactespdm.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {
    private val acb: ActivityContactBinding by lazy{
        ActivityContactBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)
        intent.getStringExtra(Name)?.let{
            acb.nameEt.setText(it)
        }
        intent.getStringExtra(Address)?.let{
            acb.addressEt.setText(it)
        }
        intent.getStringExtra(Phone)?.let{
            acb.phoneEt.setText(it)
        }
        intent.getStringExtra(Email)?.let{
            acb.emailEt.setText(it)
        }

        acb.saveBt.setOnClickListener {
            val name = acb.nameEt.text
            val phone = acb.phoneEt.text
            val email = acb.emailEt.text
            val address = acb.addressEt.text

            val result_intent = Intent()
            result_intent.putExtra(Name,name)
            result_intent.putExtra(Email,email)
            result_intent.putExtra(Address,address)
            result_intent.putExtra(Phone,phone)
            setResult(RESULT_OK,result_intent)

        }
    }
}