package com.learning.mariopatterns.model

object LifeSupply {

    private var lives = 3

    var observer: (Int) -> Unit = {}

    fun addLives(lives: Int) {
        this.lives += lives
        observer(this.lives)
    }

    fun decreaseLives() {
        lives -= 1
        observer(lives)
    }
}