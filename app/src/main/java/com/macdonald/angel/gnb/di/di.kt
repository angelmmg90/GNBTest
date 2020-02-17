package com.macdonald.angel.gnb.di

import android.app.Application
import com.macdonald.angel.gnb.data.database.GNBLocalDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(
            appModule,
            dataModule,
            scopesModule)
        )
    }
}

private val appModule = module {
    single { GNBLocalDatabase.build(get()) }
}

private val dataModule = module {
}

private val scopesModule = module {
}

