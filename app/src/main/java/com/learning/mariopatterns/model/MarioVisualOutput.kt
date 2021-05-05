package com.learning.mariopatterns.model

import androidx.annotation.LayoutRes

interface MarioVisualOutput {
    fun changeMarioAppearanceTo(@LayoutRes layout: Int)
    fun triggerFlyAnimations()
    fun triggerDeathAnimations()
    fun triggerFireballAnimation()
    fun triggerJumpAnimation()
    fun triggerSpinJumpAnimation()
    fun triggerYoshiTongue()
}