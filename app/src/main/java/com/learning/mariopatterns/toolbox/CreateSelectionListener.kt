package com.learning.mariopatterns.toolbox

import android.view.View
import android.widget.AdapterView

fun createSelectionListener(callback: (position: Int) -> Unit) =
    object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(view: AdapterView<*>?) = Unit

        override fun onItemSelected(
            view: AdapterView<*>?,
            p1: View?,
            position: Int,
            p3: Long
        ) = callback(position)
    }