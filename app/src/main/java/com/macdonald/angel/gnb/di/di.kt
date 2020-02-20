package com.macdonald.angel.gnb.di

import android.app.Application
import com.macdonald.angel.data.repositories.ProductsRepository
import com.macdonald.angel.data.repositories.RatesRepository
import com.macdonald.angel.data.repositories.TransactionsRepository
import com.macdonald.angel.data.sources.*
import com.macdonald.angel.gnb.data.database.GNBLocalDatabase
import com.macdonald.angel.gnb.data.networking.datasources.products.ConcretionProductLocalDatasource
import com.macdonald.angel.gnb.data.networking.datasources.rates.ConcretionRateLocalDatasource
import com.macdonald.angel.gnb.data.networking.datasources.rates.ConcretionRatesRemoteDatasource
import com.macdonald.angel.gnb.data.networking.datasources.transactions.ConcretionTransactionsLocalDatasource
import com.macdonald.angel.gnb.data.networking.datasources.transactions.ConcretionTransactionsRemoteDatasource
import com.macdonald.angel.gnb.ui.features.productList.ProductListFragment
import com.macdonald.angel.gnb.ui.features.productList.ProductListViewModel
import com.macdonald.angel.gnb.ui.features.rateList.RateListFragment
import com.macdonald.angel.gnb.ui.features.rateList.RateListViewModel
import com.macdonald.angel.gnb.ui.features.transactionList.TransactionsListFragment
import com.macdonald.angel.gnb.ui.features.transactionList.TransactionsListViewModel
import com.macdonald.angel.usecases.ProductsUseCases
import com.macdonald.angel.usecases.RatesUseCases
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
    single<TransactionsLocalDatasource> { ConcretionTransactionsLocalDatasource(get()) }
    single<RatesLocalDatasource> { ConcretionRateLocalDatasource(get()) }
    single<RatesRemoteDatasource> { ConcretionRatesRemoteDatasource(get()) }
    single<ProductsLocalDatasource> { ConcretionProductLocalDatasource(get()) }
}

private val dataModule = module {
    factory { TransactionsRepository(get(), get()) }
    factory { RatesRepository(get(), get()) }
    factory { ProductsRepository(get()) }
}

private val scopesModule = module {
    scope((named<TransactionsListFragment>())) {
        scoped { TransactionsUseCases(get()) }
        viewModel {
            TransactionsListViewModel(
                get(),
                get()
            )
        }
    }

    scope((named<RateListFragment>())) {
        scoped { RatesUseCases(get()) }
        viewModel {
            RateListViewModel(
                get(),
                get()
            )
        }
    }

    scope((named<ProductListFragment>())) {
        scoped { ProductsUseCases(get()) }
        scoped { TransactionsUseCases(get()) }
        viewModel {
            ProductListViewModel(
                get(),
                get(),
                get()
            )
        }
    }
}

