package com.macdonald.angel.gnb.ui.features.productDetails


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.gnb.R
import com.macdonald.angel.gnb.ui.features.productList.adapters.ProductListAdapter
import com.macdonald.angel.gnb.ui.features.transactionList.adapters.TransactionsListAdapter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_product_details.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ProductDetailsFragment : Fragment(), ProductDetailsContract.View {

    private val viewModel: ProductDetailsViewModel by currentScope.viewModel(this)

    private lateinit var adapter: TransactionsListAdapter
    private lateinit var transactionList: ArrayList<TransactionModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.model.observe(this, Observer(::updateUi))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isArguments = arguments != null
        if(isArguments){
            var args: ProductDetailsFragmentArgs = ProductDetailsFragmentArgs.fromBundle(arguments!!)
            initializeViews()
            viewModel.getProductDetailsData(args.productName)
        }else{
            canNotGetAnyData()
        }
    }

    override fun initializeViews() {
        rvProductTransactions.layoutManager = LinearLayoutManager(context!!)
    }

    override fun updateUi(model: ProductDetailsViewModel.UiModel) = when(model) {
        is ProductDetailsViewModel.UiModel.ShowProductDetailsData -> {
            transactionList = model.productDetails.transactions as ArrayList<TransactionModel>
            adapter = TransactionsListAdapter(
                context!!,
                transactionList
            ) { transactionItem: TransactionModel, _: View ->

                val toast = Toasty.info(
                    context!!,
                    transactionItem.amount.toString(),
                    Toast.LENGTH_LONG,
                    true
                )
                toast.show()

            }
            rvProductTransactions.adapter = adapter
        }
        is ProductDetailsViewModel.UiModel.UpdateProductDetails -> {
            viewModel.updateProductDetailsData(model.productDetails)
        }
        ProductDetailsViewModel.UiModel.NotProductTransactionsFoundLocally -> {
            canNotGetAnyData()
        }
        ProductDetailsViewModel.UiModel.ErrorUpdatingProductDetails -> {
            canNotGetAnyData()
        }
        ProductDetailsViewModel.UiModel.ErrorGettingTransactionsByProduct -> {
            canNotGetAnyData()
        }
    }

    override fun canNotGetAnyData() {
        val toast = Toasty.info(
            context!!,
            getString(R.string.not_get_products_details),
            Toast.LENGTH_LONG,
            true
        )
        toast.show()
    }

}
