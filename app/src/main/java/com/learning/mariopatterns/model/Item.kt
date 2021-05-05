package com.learning.mariopatterns.model

import com.learning.mariopatterns.R

sealed class Item {

    abstract fun appearance(): Int
    abstract fun applyTo(mario: Mario)

    abstract class StateChangingItem : Item() {

        override fun applyTo(mario: Mario) {
            val nextState = createNextState(mario)
            mario.applyState(nextState)

            if (mario.state !is MarioState.Tiny) {
                ItemBackup.setBackupItem(this)
            }
        }

        abstract fun createNextState(mario: Mario): MarioState
    }

    object RedMushroom : StateChangingItem() {

        override fun appearance(): Int =
            R.drawable.item_red_mushroom

        override fun createNextState(mario: Mario) = MarioState.Normal(mario)
    }

    object FireFlower : StateChangingItem() {

        override fun appearance(): Int =
            R.drawable.item_flower

        override fun createNextState(mario: Mario) = MarioState.FireThrower(mario)
    }

    object Feather : StateChangingItem() {

        override fun appearance(): Int =
            R.drawable.item_feather

        override fun createNextState(mario: Mario) = MarioState.Cloaked(mario)
    }

    object Star : Item() {

        override fun appearance(): Int =
            R.drawable.item_star

        override fun applyTo(mario: Mario) {
            val stateDecorator = MarioState.Invincible(mario, mario.state)
            mario.applyState(stateDecorator)
        }
    }

    object GreenMushroom : Item() {

        override fun appearance(): Int =
            R.drawable.item_green_mushroom

        override fun applyTo(mario: Mario) =
            LifeSupply.addLives(1)
    }

    object YoshiEgg : Item() {

        override fun appearance(): Int =
            R.drawable.item_yoshi_egg

        override fun applyTo(mario: Mario) {
            val nextState = MarioState.Yoshi(mario, mario.state)
            mario.applyState(nextState)
        }
    }
}