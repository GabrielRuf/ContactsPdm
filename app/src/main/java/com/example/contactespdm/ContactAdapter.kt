package com.example.contactespdm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.contactespdm.model.Contact

class ContactAdapter(context: Context, contacts: List<Contact>) : ArrayAdapter<Contact>(context, 0, contacts) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        }

        val contact = getItem(position)
        listItemView?.findViewById<TextView>(android.R.id.text1)?.text = contact.toString()

        return listItemView!!
    }
}
