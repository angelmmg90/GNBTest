package com.macdonald.angel.gnb.ui.features.productList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.gnb.R
import com.macdonald.angel.gnb.ui.features.productList.adapters.ProductListAdapter
import es.dmoral.toasty.Toasty
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
        viewModel.getAllProductsFromLocal()
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

                val toast = Toasty.info(
                    context!!,
                    "Item clicked",
                    Toast.LENGTH_LONG,
                    true
                )
                toast.show()
            }
            rvProducts.adapter = adapter
        }
        is ProductListViewModel.UiModel.InsertProducts -> {
            viewModel.insertAllProducts(model.productList)
        }
        ProductListViewModel.UiModel.Forbbiden -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.not_get_products),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()
        }
        ProductListViewModel.UiModel.NotProductDataFoundLocally -> {
            viewModel.getProductsFromLocalTransactions()
        }
        ProductListViewModel.UiModel.NetWorkError -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.not_get_products),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()
        }
        ProductListViewModel.UiModel.ErrorInsertingProducts -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.not_get_products),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()

        }
        ProductListViewModel.UiModel.ErrorGettingTransactions -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.not_get_products),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()

        }
        ProductListViewModel.UiModel.ErrorGettingLocalTransactions -> {
            viewModel.getProductsFromRemoteTransactions()
        }
    }


}
