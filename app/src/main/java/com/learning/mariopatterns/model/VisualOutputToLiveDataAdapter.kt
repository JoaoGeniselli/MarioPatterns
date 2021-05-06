package com.learning.mariopatterns.model

import androidx.lifecycle.MutableLiveData

class VisualOutputToLiveDataAdapter(
    private val appearanceLiveData: MutableLiveData<Int>,
    private val messageLiveData: MutableLiveData<String>
) : MarioVisualOutput {

    override fun changeMarioAppearanceTo(layout: Int) {
        appearanceLiveData.value = layout
    }

    override fun triggerFlyAnimations() {
        messageLiveData.value = "Vamos voar!"
    }

    override fun triggerDeathAnimations() {
        messageLiveData.value = "Mario morreu!"
    }

    override fun triggerFireballAnimation() {
        messageLiveData.value = "Mario atirou"
    }

    override fun triggerJumpAnimation() {
        messageLiveData.value = "Mario pulou"
    }

    override fun triggerSpinJumpAnimation() {
        messageLiveData.value = "Mario pulou girando"
    }

    override fun triggerYoshiTongue() {
        messageLiveData.value = "Mario acionou o Yoshi"
    }
}