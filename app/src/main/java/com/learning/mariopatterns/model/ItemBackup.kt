package com.learning.mariopatterns.model

object ItemBackup {

    var backupListener: (Item?) -> Unit = {}
    private var backedUpItem: Item? = null

    fun setBackupItem(item: Item) {
        backedUpItem = item
        backupListener(item)
    }

    fun releaseItem(): Item? {
        val item = backedUpItem
        backedUpItem = null
        backupListener(null)
        return item
    }
}