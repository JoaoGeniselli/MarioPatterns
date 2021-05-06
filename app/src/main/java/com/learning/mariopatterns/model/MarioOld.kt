package com.learning.mariopatterns.model

import android.os.Handler
import android.os.Looper
import com.learning.mariopatterns.R
import java.util.*

class MarioOld {

    var visualOutput: MarioVisualOutput? = null
    var deathObserver: () -> Unit = {}
    var hitObserver:  () -> Unit = {}

    var status: String = "tiny"
    var previousStatus: String = ""

    fun die() {
        // Sem observer: Não possibilita adicionar mais ações ao callback de die()
        visualOutput?.triggerDeathAnimations()
        LifeSupply.decreaseLives()
    }

    fun fly() {
        visualOutput?.triggerFlyAnimations()
    }

    fun throwFireball() {
        visualOutput?.triggerFireballAnimation()
    }

    fun takeHit() {
        when (status) {
            "invincible" -> Unit
            "tiny" -> die()
            "normal" -> {
                status = "tiny"
                updateAppearance()
            }
            "cloaked" -> {
                status = previousStatus
                previousStatus = ""
                ItemBackup.releaseItem()?.let {
                    useItem(it)
                }
                updateAppearance()
            }
            "fire" -> {
                status = previousStatus
                previousStatus = ""
                ItemBackup.releaseItem()?.let {
                    useItem(it)
                }
                updateAppearance()
            }
            "yoshi" -> {
                status = previousStatus
                previousStatus = ""
                updateAppearance()
                ItemBackup.releaseItem()?.let {
                    useItem(it)
                }
            }
        }
        hitObserver()
    }

    private fun updateAppearance() {
        val appearance = when(status) {
            "normal" -> R.drawable.mario_normal
            "fire" -> R.drawable.mario_fire
            "cloaked" -> R.drawable.mario_cloak
            "yoshi" -> R.drawable.mario_yoshi
            "invincible" -> R.drawable.mario_invincible
            else -> R.drawable.mario_tiny
        }
        visualOutput?.changeMarioAppearanceTo(appearance)
    }

    fun runCommand(command: String) {
        when {
            status == "cloaked" && command == "(right + Y x 2s) + B" -> fly()
            status == "fire" && command == "Y" -> throwFireball()
            status == "yoshi" && command == "Y" -> triggerYoshiTongue()
            status == "yoshi" && command == "A" -> {
                status = previousStatus
                updateAppearance()
            }
            command == "B" -> jump()
            command == "B" -> spinJump()
        }
    }

    private fun jump() {
        visualOutput?.triggerJumpAnimation()
    }

    private fun spinJump() {
        visualOutput?.triggerSpinJumpAnimation()
    }

    fun triggerYoshiTongue() {
        visualOutput?.triggerYoshiTongue()
    }

    fun useItem(item: Item) {
        when (item) {
            Item.GreenMushroom -> LifeSupply.addLives(1)
            Item.FireFlower -> updateStatus("fire")
            Item.RedMushroom -> updateStatus("normal")
            Item.Star -> {
                updateStatus("invincible")
                schedule(6000L) {
                    updateStatus(previousStatus)
                }
            }
            Item.Feather -> updateStatus("cloaked")
            Item.YoshiEgg -> updateStatus("yoshi")
            else -> Unit
        }
    }

    private fun schedule(time: Long, function: () -> Unit) {
        val task = object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post {
                    function()
                }
            }
        }
        Timer().schedule(task, time)
    }

    private fun updateStatus(status: String) {
        previousStatus = status
        this.status = status
        updateBackupItem()
        updateAppearance()
    }

    private fun updateBackupItem() {
        val backup = when(previousStatus) {
            "normal" -> Item.RedMushroom
            "fire" -> Item.FireFlower
            "cloaked" -> Item.Feather
            else -> null
        }
        ItemBackup.setBackupItem(backup)
    }
}