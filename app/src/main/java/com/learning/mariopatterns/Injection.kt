package com.learning.mariopatterns

import com.learning.mariopatterns.presentation.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Injection {

    val module by lazy {
        module {
            viewModel { MainViewModel() }
        }
    }
}