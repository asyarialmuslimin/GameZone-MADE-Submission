@file:Suppress("unused")

package com.saifur.gamezone.favourite

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FavouriteViewModel(get()) }
}

@JvmField
val favouriteModule = module {
    includes(viewModelModule)
}