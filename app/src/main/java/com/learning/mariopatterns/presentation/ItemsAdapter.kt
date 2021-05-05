package com.learning.mariopatterns.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.learning.mariopatterns.R
import com.learning.mariopatterns.model.Item

class ItemsAdapter(
    context: Context,
    private val items: List<Item>
) : BaseAdapter() {

    private val mInflater = LayoutInflater.from(context);

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position) as? Item
        val view = convertView ?: mInflater.inflate(
            R.layout.view_spinner,
            parent,
            false
        )
        item?.let {
            view.findViewById<ImageView>(R.id.image)?.setImageResource(it.appearance())
        }
        return view
    }

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getCount(): Int = items.size
}