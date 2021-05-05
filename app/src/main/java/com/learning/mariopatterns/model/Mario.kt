package com.learning.mariopatterns.model

class Mario {

    var visualOutput: MarioVisualOutput? = null
    var deathObserver: () -> Unit = {}
    var hitObserver:  () -> Unit = {}

    var state: MarioState = MarioState.Tiny(this)

    fun die() {
        visualOutput?.triggerDeathAnimations()
    }

    fun applyState(state: MarioState) {
        this.state = state
        visualOutput?.changeMarioAppearanceTo(state.appearance())
    }

    fun fly() {
        visualOutput?.triggerFlyAnimations()
    }

    fun throwFireball() {
        visualOutput?.triggerFireballAnimation()
    }

    fun takeHit() {
        state.takeDamage()
    }

    fun runCommand(command: String) {
        val isHandled = state.recognizeSpecialCommand(command)
        if (isHandled.not()) {
            when (command) {
                "B" -> jump()
                "A" -> spinJump()
            }
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
}