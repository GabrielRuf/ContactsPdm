package com.example.contactespdm.ui
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.example.contactespdm.databinding.ActivityContactBinding
import com.example.contactespdm.model.Contact

class ContactActivity : AppCompatActivity() {
    private val acb: ActivityContactBinding by lazy{
        ActivityContactBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)
        val receivedContact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra("EXTRA_CONTACT",Contact::class.java)
        }
        else
        {
            intent.getParcelableExtra<Contact>("EXTRA_CONTACT")
        }

        with(acb) {
            receivedContact?.let {
                nameEt.setText(receivedContact.name)
                addressEt.setText(receivedContact.address)
                phoneEt.setText(receivedContact.phone)
                emailEt.setText(receivedContact.email)
                if (intent.getBooleanExtra("EXTRA_VIEW_CONTACT",false)){
                    nameEt.isEnabled = false
                    addressEt.isEnabled = false
                    phoneEt.isEnabled = false
                    emailEt.isEnabled = false
                    saveBt.visibility = View.GONE
                }
            }
            saveBt.setOnClickListener{
                Contact(
                    id = receivedContact?.id?:hashCode(),
                    name = nameEt.text.toString(),
                    address = addressEt.text.toString(),
                    phone = phoneEt.text.toString(),
                    email = emailEt.text.toString()
                ).let {
                    contact ->
                    Intent().apply {
                        putExtra("EXTRA_CONTACT",contact)
                        setResult(RESULT_OK,this)
                        finish()
                    }
                }
            }
        }

    }
}