package com.learning.mariopatterns

object ItemBackup {

    var backedUpItem: Item? = null

    fun setBackupItem(item: Item) {
        backedUpItem = item
    }
}