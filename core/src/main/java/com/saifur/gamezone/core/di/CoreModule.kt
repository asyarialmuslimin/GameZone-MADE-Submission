package com.saifur.gamezone.core.di

import com.saifur.gamezone.core.data.source.local.config.AppDatabase
import com.saifur.gamezone.core.data.source.local.datasource.ILocalDataSource
import com.saifur.gamezone.core.data.source.local.datasource.LocalDataSource
import com.saifur.gamezone.core.data.source.remote.config.ApiService
import com.saifur.gamezone.core.data.source.remote.datasource.IRemoteDataSource
import com.saifur.gamezone.core.data.source.remote.datasource.RemoteDataSource
import com.saifur.gamezone.core.data.source.repository.GameRepository
import com.saifur.gamezone.core.domain.repository.IGameRepository
import com.saifur.gamezone.core.domain.usecase.GameUseCase
import com.saifur.gamezone.core.domain.usecase.IGameUseCase
import org.koin.dsl.module

val dataModule = module {
    single { ApiService.getRetrofit() }
    single { AppDatabase.getInstance(get()).gameListDao() }
    single { AppDatabase.getInstance(get()).gameDetailDao() }
}

val dataSourceModule = module {
    single<IRemoteDataSource> { RemoteDataSource(get()) }
    single<ILocalDataSource> { LocalDataSource(get(), get()) }
}

val repositoryModule = module {
    single<IGameRepository> { GameRepository(get(), get()) }
}

val useCaseModule = module {
    single<IGameUseCase> { GameUseCase(get()) }
}

val coreModule = module {
    includes(dataModule, dataSourceModule, repositoryModule, useCaseModule)
}