package com.macdonald.angel.gnb.ui.features.productList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.gnb.R
import com.macdonald.angel.gnb.common.hide
import com.macdonald.angel.gnb.common.messageToShow
import com.macdonald.angel.gnb.common.visible
import com.macdonald.angel.gnb.ui.features.productList.adapters.ProductListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ProductListFragment : Fragment(), ProductListContract.View {

    private val viewModel: ProductListViewModel by currentScope.viewModel(this)

    private lateinit var adapter: ProductListAdapter
    private lateinit var listProducts: ArrayList<ProductModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.model.observe(this, Observer(::updateUi))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.requireActivity().progressBar.visible()
        initializeViews()
        viewModel.loadData()
    }

    override fun initializeViews() {
        rvProducts.layoutManager = GridLayoutManager(context!!, 2)
    }

    override fun updateUi(model: ProductListViewModel.UiModel) = when (model) {
        is ProductListViewModel.UiModel.ShowProducts -> {
            listProducts = model.productList as ArrayList<ProductModel>
            adapter = ProductListAdapter(
                context!!,
                listProducts
            ) { productItem: ProductModel, _: View ->

                val productDetailScreen =
                    ProductListFragmentDirections.goProductDetailAction(productItem.name)
                findNavController().navigate(productDetailScreen)

            }
            rvProducts.adapter = adapter
            this.requireActivity().progressBar.hide()
        }
        is ProductListViewModel.UiModel.InsertProducts -> {
            viewModel.insertAllProducts(model.productList)
        }
        ProductListViewModel.UiModel.NotProductDataFoundLocally -> {
            viewModel.getProductsFromLocalTransactions()
        }
        ProductListViewModel.UiModel.NetWorkError -> {
            messageToShow(getString(R.string.network_error), true)
            this.requireActivity().progressBar.hide()
        }
        ProductListViewModel.UiModel.ErrorInsertingProducts -> {
            messageToShow(getString(R.string.not_get_products), true)
            this.requireActivity().progressBar.hide()
        }
        ProductListViewModel.UiModel.ErrorGettingTransactions -> {
            messageToShow(getString(R.string.not_get_products), false)
            this.requireActivity().progressBar.hide()
        }
        ProductListViewModel.UiModel.ErrorGettingLocalTransactions -> {
            viewModel.getProductsFromRemoteTransactions()
        }
        ProductListViewModel.UiModel.ErrorGettingRates -> {
            messageToShow(getString(R.string.not_rates_downloaded), false)
            this.requireActivity().progressBar.hide()
        }
    }

}
