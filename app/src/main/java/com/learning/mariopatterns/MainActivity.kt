package com.learning.mariopatterns

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.DrawableRes

class MainActivity : AppCompatActivity() {

    private val mario: Mario = Mario(object : VisualOutput {
        override fun changeMarioAppearanceTo(@DrawableRes layout: Int) {
            findViewById<ImageView>(R.id.mario_appearance)?.setImageResource(layout)
        }

        override fun triggerFlyAnimations() {
            findViewById<TextView>(R.id.state_log)?.text = "Voar iniciado"
        }

        override fun triggerDeathAnimations() {
            findViewById<TextView>(R.id.state_log)?.text = "Mario morreu"
        }

        override fun triggerFireballAnimation() {
            findViewById<TextView>(R.id.state_log)?.text = "Bola de fogo disparada"
        }

        override fun triggerJumpAnimation() {
            findViewById<TextView>(R.id.state_log)?.text = "Pulou normal"
        }

        override fun triggerSpinJumpAnimation() {
            findViewById<TextView>(R.id.state_log)?.text = "Pulou girando"
        }

        override fun triggerYoshiTongue() {
            findViewById<TextView>(R.id.state_log)?.text = "Acionou o Yoshi"
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showItems()
    }

    private fun showItems() {
        val spinner = findViewById<Spinner>(R.id.spinner_item)
        val items = listOf(
            Item.RedMushroom,
            Item.GreenMushroom,
            Item.Feather,
            Item.FireFlower,
            Item.Star,
            Item.YoshiEgg
        )

        spinner.apply {
            adapter = Adapter(this@MainActivity, items)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(p0: AdapterView<*>?) = Unit

                override fun onItemSelected(view: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    items[position].applyTo(mario)
                }
            }
        }

        findViewById<Button>(R.id.button_y).setOnClickListener {
            mario.runCommand("Y")
        }
        findViewById<Button>(R.id.button_b).setOnClickListener {
            mario.runCommand("B")
        }
        findViewById<Button>(R.id.button_a).setOnClickListener {
            mario.runCommand("A")
        }
        findViewById<Button>(R.id.button_damage).setOnClickListener {
            mario.takeHit()
        }
        findViewById<Button>(R.id.button_fly).setOnClickListener {
            mario.runCommand("(right + Y x 2s) + B")
        }
    }

}