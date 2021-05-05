package com.learning.mariopatterns.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learning.mariopatterns.model.Item
import com.learning.mariopatterns.model.ItemBackup
import com.learning.mariopatterns.model.LifeSupply
import com.learning.mariopatterns.model.Mario
import kotlin.math.max

class MainViewModel(
    private val mario: Mario
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> get() = _items

    private val _marioAppearance = MutableLiveData<Int>()
    val marioAppearance: LiveData<Int> get() = _marioAppearance

    private val _backupIteIm = MutableLiveData<Int?>()
    val backupItem: LiveData<Int?> get() = _backupIteIm

    private val _lives = MutableLiveData<Int>()
    val lives: LiveData<Int> get() = _lives

    private val itemList = listOf(
        Item.RedMushroom,
        Item.GreenMushroom,
        Item.Feather,
        Item.FireFlower,
        Item.Star,
        Item.YoshiEgg
    )

    fun init() {
        ItemBackup.backupListener = { item ->
            _backupIteIm.value = item?.appearance()
        }
        LifeSupply.observer = { lives ->
            _lives.value = max(0, lives)
        }
        mario.apply {
            deathObserver = {
                LifeSupply.decreaseLives()
            }
            hitObserver = {
                val backup = ItemBackup.releaseItem()
                backup?.applyTo(mario)
            }
        }
        _items.value = itemList
    }

    fun onDamagePressed() = mario.takeHit()

    fun onItemSelected(position: Int) {
        val selectedItem = itemList[position]
        selectedItem.applyTo(mario)
    }

    fun onControlEvent(event: String) {
        mario.runCommand(event)
    }
}