package com.example.contactespdm.ui

//noinspection SuspiciousImport
import android.R
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

    private lateinit var carl : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        carl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result -> if (result.resultCode == RESULT_OK){
                val contact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    result.data?.getParcelableExtra("EXTRA_CONTACT",Contact::class.java)
                }
                else
                {
                    result.data?.getParcelableExtra<Contact>("EXTRA_CONTACT")
                }
            contact?.let { newOrEditedContact ->
                val position = contactList.indexOfFirst { it.id == newOrEditedContact.id }
                if (position != -1){
                    contactList[position] = newOrEditedContact
                }
                else{
                    contactList.add(contact)
                }
                contactAdapter.notifyDataSetChanged()
            }

        }
        }

        fillContacts()

        registerForContextMenu(amb.contactesLv)
        amb.contactesLv.setOnItemClickListener { _, _, position, _ ->
            val contact = contactList[position]
            val viewContactIntent = Intent(this@MainActivity,ContactActivity::class.java)
            viewContactIntent.putExtra("EXTRA_CONTACT",contact)
            viewContactIntent.putExtra("EXTRA_VIEW_CONTACT", true)
            startActivity(viewContactIntent)
        }

        amb.contactesLv.adapter = contactAdapter
    }



    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(com.example.contactespdm.R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position
        return when(item.itemId){
            com.example.contactespdm.R.id.removeContactMi -> {
                contactList.removeAt(position)
                contactAdapter.notifyDataSetChanged()
                Toast.makeText(this,"Removido",Toast.LENGTH_LONG).show()
                true
            }
            com.example.contactespdm.R.id.editContactMi -> {
                carl.launch(
                    Intent(this,ContactActivity::class.java).apply{
                        putExtra("EXTRA_CONTACT",contactList[position])
                    }
                )
                true
            }
            else -> {false}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterForContextMenu(amb.contactesLv)
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