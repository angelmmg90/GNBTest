package com.macdonald.angel.gnb.ui.features.transactionList

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.domain.transactionsUseCase.TransactionDomain
import com.macdonald.angel.usecases.TransactionsUseCases
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TransactionsListViewModelTest {
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var transactionsUseCases: TransactionsUseCases

    @Mock
    lateinit var context: Application

    @Mock
    lateinit var observer: Observer<TransactionsListViewModel.UiModel>

    private lateinit var vm: TransactionsListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        vm = TransactionsListViewModel(context, transactionsUseCases)
        vm.model.observeForever(observer)
    }

    @Test
    fun `get transaction list from database successfully`() {
        runBlockingTest {
            val listTransactions = listOf(
                TransactionModel(
                    "J258",
                    25.10,
                    "AUD"
                )
            )
            whenever(transactionsUseCases.getTransactionsFromLocal()).
                thenReturn(listTransactions)

            runBlockingTest {vm.getAllTransactionsFromLocal()}

            verify(
                observer,
                times(1)
            ).onChanged((vm.model.value as
                    TransactionsListViewModel.UiModel.ShowTransactions))

            var currentStatus =
                (vm.model.value as TransactionsListViewModel.UiModel.ShowTransactions).transactionList
            assertEquals(currentStatus[0].product, listTransactions[0].product)
        }
    }

    @Test
    fun `get transaction list from remote successfully`() {
        runBlocking {
            val transactionsDomain = arrayOf(
                TransactionDomain(
                    "J258",
                    25.10,
                    "AUD"
                )
            )

            val response: Response<Array<TransactionDomain>> = Response.Success(transactionsDomain)
            whenever(transactionsUseCases.getTransactionsFromRemote()).thenReturn(response)
            val serverResponse = transactionsUseCases.getTransactionsFromRemote()
            assertEquals(serverResponse, response)
        }
    }
}