package com.macdonald.angel.gnb.ui.features.rateList

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.macdonald.angel.data.model.RateModel
import com.macdonald.angel.data.repositories.Response
import com.macdonald.angel.domain.ratesUseCase.RateDomain
import com.macdonald.angel.usecases.RatesUseCases
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RateListViewModelTest {
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var ratesUseCases: RatesUseCases

    @Mock
    lateinit var context: Application

    @Mock
    lateinit var observer: Observer<RateListViewModel.UiModel>

    private lateinit var vm: RateListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        vm = RateListViewModel(context, ratesUseCases)
        vm.model.observeForever(observer)
    }

    @Test
    fun `get rate list from database successfully`() {
        runBlockingTest {
            val rateList = listOf(
                RateModel(
                    "AUD",
                    "EUR",
                    1.14
                )
            )
            whenever(ratesUseCases.getRatesFromLocal()).
                thenReturn(rateList)

            runBlockingTest {vm.getAllRatesFromLocal()}

            verify(
                observer,
                times(1)
            ).onChanged((vm.model.value as
                    RateListViewModel.UiModel.ShowRates))

            var currentStatus =
                (vm.model.value as RateListViewModel.UiModel.ShowRates).rateList
            assertEquals(currentStatus[0].rate, rateList[0].rate)
        }
    }

    @Test
    fun `get rate list from remote successfully`() {
        runBlocking {
            val ratesDomain = arrayOf(
                RateDomain(
                    "AUD",
                    "EUR",
                    1.14
                )
            )

            val response: Response<Array<RateDomain>> = Response.Success(ratesDomain)
            whenever(ratesUseCases.getRatesFromRemote()).thenReturn(response)
            val serverResponse = ratesUseCases.getRatesFromRemote()
            assertEquals(serverResponse, response)
        }
    }
}