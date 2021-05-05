package com.learning.mariopatterns

object LifeSupply {

    private var lives = 3

    fun addLives(lives: Int) {
        this.lives += lives
    }
}