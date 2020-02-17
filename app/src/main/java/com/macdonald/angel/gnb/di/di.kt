package com.macdonald.angel.gnb.di

import android.app.Application
import com.macdonald.angel.data.repositories.TransactionsRepository
import com.macdonald.angel.data.sources.TransactionsRemoteDatasource
import com.macdonald.angel.gnb.data.database.GNBLocalDatabase
import com.macdonald.angel.gnb.data.networking.datasources.transactions.ConcretionTransactionsRemoteDatasource
import com.macdonald.angel.gnb.ui.TransactionsListFragment
import com.macdonald.angel.gnb.ui.TransactionsListViewModel
import com.macdonald.angel.usecases.TransactionsUseCases
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
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
    single<TransactionsRemoteDatasource> { ConcretionTransactionsRemoteDatasource(get()) }
}

private val dataModule = module {
    factory { TransactionsRepository(get()) }
}

private val scopesModule = module {
    scope((named<TransactionsListFragment>())) {
        scoped { TransactionsUseCases(get()) }
        viewModel { TransactionsListViewModel(get(), get()) }
    }
}

