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
import com.macdonald.angel.gnb.common.messageToShow
import com.macdonald.angel.gnb.ui.features.productDetails.adapters.ProductTransactionsDetailListAdapter
import com.macdonald.angel.gnb.ui.features.productList.adapters.ProductListAdapter
import com.macdonald.angel.gnb.ui.features.transactionList.adapters.TransactionsListAdapter
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.model.observe(this, Observer(::updateUi))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        viewModel.loadData()
    }

    override fun initializeViews() {
        rvProducts.layoutManager = GridLayoutManager(context!!, 2)
    }

    override fun updateUi(model: ProductListViewModel.UiModel) = when(model) {
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
        }
        is ProductListViewModel.UiModel.InsertProducts -> {
            viewModel.insertAllProducts(model.productList)
        }
        ProductListViewModel.UiModel.NotProductDataFoundLocally -> {
            viewModel.getProductsFromLocalTransactions()
        }
        ProductListViewModel.UiModel.NetWorkError -> {
            messageToShow(getString(R.string.network_error), true)
        }
        ProductListViewModel.UiModel.ErrorInsertingProducts -> {
            messageToShow(getString(R.string.not_get_products), true)
        }
        ProductListViewModel.UiModel.ErrorGettingTransactions -> {
            messageToShow(getString(R.string.not_get_products), false)
        }
        ProductListViewModel.UiModel.ErrorGettingLocalTransactions -> {
            viewModel.getProductsFromRemoteTransactions()
        }
        ProductListViewModel.UiModel.ErrorGettingRates -> {
            messageToShow(getString(R.string.not_rates_downloaded), false)
        }
    }

}
