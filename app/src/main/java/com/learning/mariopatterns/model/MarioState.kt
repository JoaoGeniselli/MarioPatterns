package com.learning.mariopatterns.model

import android.os.Handler
import android.os.Looper
import com.learning.mariopatterns.R
import java.util.*


sealed class MarioState(protected val mario: Mario) {

    abstract fun itemToBackup(): Item?
    abstract fun appearance(): Int
    abstract fun takeDamage()
    abstract fun recognizeSpecialCommand(command: String): Boolean

    class Tiny(mario: Mario) : MarioState(mario) {

        override fun itemToBackup(): Item? = null

        override fun appearance(): Int =
            R.drawable.mario_tiny

        override fun takeDamage() = mario.die()

        override fun recognizeSpecialCommand(command: String): Boolean {
            // Nothing special
            return false
        }
    }

    class Normal(mario: Mario) : MarioState(mario) {

        override fun itemToBackup(): Item? = Item.RedMushroom

        override fun appearance(): Int =
            R.drawable.mario_normal

        override fun takeDamage() = mario.applyState(
            Tiny(
                mario
            )
        )

        override fun recognizeSpecialCommand(command: String): Boolean {
            // Nothing special
            return false
        }
    }

    class Cloaked(mario: Mario) : MarioState(mario) {

        override fun itemToBackup(): Item? = Item.Feather

        override fun appearance(): Int =
            R.drawable.mario_cloak

        override fun takeDamage() = mario.applyState(
            Tiny(
                mario
            )
        )

        override fun recognizeSpecialCommand(command: String): Boolean {
            val runThenJump = "(right + Y x 2s) + B";
            if (command == runThenJump) {
                mario.fly()
                return true
            }
            return false
        }
    }

    class FireThrower(mario: Mario) : MarioState(mario) {

        override fun itemToBackup(): Item? = Item.FireFlower

        override fun appearance(): Int =
            R.drawable.mario_fire

        override fun takeDamage() = mario.applyState(
            Tiny(
                mario
            )
        )

        override fun recognizeSpecialCommand(command: String): Boolean {
            if (command == "Y") {
                mario.throwFireball()
                return true
            }
            return false
        }
    }

    class Invincible(mario: Mario, private val wrapped: MarioState) : MarioState(mario) {

        override fun itemToBackup(): Item? = wrapped.itemToBackup()

        init {
            scheduleStateReset(mario)
        }

        private fun scheduleStateReset(mario: Mario) {
            val task = object : TimerTask() {
                override fun run() {
                    Handler(Looper.getMainLooper()).post {
                        mario.applyState(wrapped)
                    }
                }
            }
            Timer().schedule(
                task,
                INVINCIBLE_PERIOD
            )
        }

        override fun appearance(): Int =
            R.drawable.mario_invincible

        override fun takeDamage() {
            // does not take effect
        }

        override fun recognizeSpecialCommand(command: String): Boolean {
            // Uses the current state
            return wrapped.recognizeSpecialCommand(command)
        }

        companion object {
            private const val INVINCIBLE_PERIOD = 6000L
        }
    }

    class Yoshi(mario: Mario, private val previousState: MarioState) : MarioState(mario) {

        override fun itemToBackup(): Item? = previousState.itemToBackup()

        override fun appearance(): Int =
            R.drawable.mario_yoshi

        override fun takeDamage() = dismountYoshi()

        override fun recognizeSpecialCommand(command: String) =
            when (command) {
                "A" -> {
                    dismountYoshi()
                    true
                }
                "Y" -> {
                    mario.triggerYoshiTongue()
                    true
                }
                else -> false
            }

        private fun dismountYoshi() = mario.applyState(previousState)

    }
}