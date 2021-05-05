package com.learning.mariopatterns

import androidx.annotation.LayoutRes

interface VisualOutput {


    fun changeMarioAppearanceTo(@LayoutRes layout: Int)
    fun triggerFlyAnimations()
    fun triggerDeathAnimations()
    fun triggerFireballAnimation()
    fun triggerJumpAnimation()
    fun triggerSpinJumpAnimation()
    fun triggerYoshiTongue()
}