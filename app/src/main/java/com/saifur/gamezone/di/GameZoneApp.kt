package com.saifur.gamezone.di

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.saifur.gamezone.core.di.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class GameZoneApp : SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@GameZoneApp)
            modules(appModule, coreModule)
        }
    }

    fun injectDynamicModules() {
        val modules = loadDynamicFeatureModules()
        if (modules.isNotEmpty()) loadKoinModules(modules)
    }

    private fun loadDynamicFeatureModules(): List<Module> {
        return try {
            val clazz = Class.forName("com.saifur.gamezone.favourite.FavouriteModuleKt")
            val module = clazz.getDeclaredField("favouriteModule").apply { isAccessible = true }
                .get(null) as Module
            listOf(module)
        } catch (e: Exception) {
            emptyList()
        }
    }
}