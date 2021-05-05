package com.learning.mariopatterns.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.learning.mariopatterns.R
import com.learning.mariopatterns.databinding.ActivityMainBinding
import com.learning.mariopatterns.model.Item
import com.learning.mariopatterns.toolbox.createSelectionListener
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel by viewModel<MainViewModel>()
    private var binding: ActivityMainBinding? = null

//    private val mario: Mario = Mario(object : VisualOutput {
//        override fun changeMarioAppearanceTo(@DrawableRes layout: Int) {
////            findViewById<ImageView>(R.id.mario_appearance)?.setImageResource(layout)
//        }
//
//        override fun triggerFlyAnimations() {
//            findViewById<TextView>(R.id.state_log)?.text = "Voar iniciado"
//        }
//
//        override fun triggerDeathAnimations() {
//            findViewById<TextView>(R.id.state_log)?.text = "Mario morreu"
//        }
//
//        override fun triggerFireballAnimation() {
//            findViewById<TextView>(R.id.state_log)?.text = "Bola de fogo disparada"
//        }
//
//        override fun triggerJumpAnimation() {
//            findViewById<TextView>(R.id.state_log)?.text = "Pulou normal"
//        }
//
//        override fun triggerSpinJumpAnimation() {
//            findViewById<TextView>(R.id.state_log)?.text = "Pulou girando"
//        }
//
//        override fun triggerYoshiTongue() {
//            findViewById<TextView>(R.id.state_log)?.text = "Acionou o Yoshi"
//        }
//    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupButtons()
        viewModel.apply {
            message.observe(this@MainActivity, Observer { logMessage(it) })
            items.observe(this@MainActivity, Observer { showItems(it) })
            marioAppearance.observe(this@MainActivity, Observer { updateMario(it) })
            init()
        }
    }

    private fun logMessage(message: String) {
        binding?.stateLog?.text = message
    }

    private fun showItems(items: List<Item>) {
        binding?.spinnerItem?.apply {
            adapter = ItemsAdapter(
                this@MainActivity,
                items
            )
            onItemSelectedListener =
                createSelectionListener { position ->
                    viewModel.onItemSelected(position)
                }
        }
    }

    private fun setupButtons() {
        binding?.apply {
            buttonY.setOnClickListener {
                viewModel.onControlEvent("Y")
            }
            buttonB.setOnClickListener {
                viewModel.onControlEvent("B")
            }
            buttonA.setOnClickListener {
                viewModel.onControlEvent("A")
            }
            buttonDamage.setOnClickListener {
                viewModel.onDamagePressed()
            }
            buttonFly.setOnClickListener {
                viewModel.onControlEvent("(right + Y x 2s) + B")
            }
        }
    }

    private fun updateMario(layout: Int) {
        binding?.scenarioContainer?.marioAppearance?.setImageResource(layout)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}