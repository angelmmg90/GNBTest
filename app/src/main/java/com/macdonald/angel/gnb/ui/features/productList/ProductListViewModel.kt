package com.macdonald.angel.gnb.ui.features.productList

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.gnb.common.ScopedViewModel
import com.macdonald.angel.usecases.ProductsUseCases
import kotlinx.coroutines.Job

class ProductListViewModel(
    private val ctx: Application,
    private val productUseCases: ProductsUseCases
):ScopedViewModel(), ProductListContract.ViewModel {

    private lateinit var getProductsJob: Job

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        class ShowProducts(val productList: List<ProductModel>) : UiModel()
        object Forbbiden : UiModel()
        object ErrorGettingProducts : UiModel()
        object NetWorkError : UiModel()
    }

    init {
        initScope()
    }

    fun cancelJobs() {
        if (::getProductsJob.isInitialized && getProductsJob.isActive) {
            getProductsJob.cancel()
        }
    }

    override fun getAllProducts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertAllProducts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}