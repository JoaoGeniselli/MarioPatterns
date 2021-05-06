package com.learning.mariopatterns.model

import androidx.annotation.DrawableRes

interface MarioVisualOutput {
    fun changeMarioAppearanceTo(@DrawableRes layout: Int)
    fun triggerFlyAnimations()
    fun triggerDeathAnimations()
    fun triggerFireballAnimation()
    fun triggerJumpAnimation()
    fun triggerSpinJumpAnimation()
    fun triggerYoshiTongue()
}