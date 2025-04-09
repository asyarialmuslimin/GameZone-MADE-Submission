package com.saifur.gamezone.di

import com.saifur.gamezone.view.detail.DetailViewModel
import com.saifur.gamezone.view.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

val appModule = module {
    includes(viewModelModule)
}