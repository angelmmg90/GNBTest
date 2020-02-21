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
import com.macdonald.angel.data.model.CurrencyType
import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.data.model.TransactionDetailsModel
import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.gnb.R
import com.macdonald.angel.gnb.common.messageToShow
import com.macdonald.angel.gnb.data.toTransactionModelList
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
    private lateinit var transactionList: ArrayList<TransactionDetailsModel>

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
            messageToShow(getString(R.string.not_get_products_details), true)
        }
    }

    override fun initializeViews() {
        rvProductTransactions.layoutManager = LinearLayoutManager(context!!)
    }

    override fun updateUi(model: ProductDetailsViewModel.UiModel) = when(model) {
        is ProductDetailsViewModel.UiModel.ShowProductDetailsData -> {
            transactionList = model.productDetails.transactions as ArrayList<TransactionDetailsModel>
            adapter = TransactionsListAdapter(
                context!!,
                transactionList.toTransactionModelList() //TODO change this later
            ) { transactionItem: TransactionModel, _: View ->
                messageToShow(transactionItem.amount.toString(), false)
            }
            rvProductTransactions.adapter = adapter
        }
        is ProductDetailsViewModel.UiModel.UpdateProductDetails -> {
            viewModel.updateProductDetailsData(model.productDetails)
        }
        ProductDetailsViewModel.UiModel.NotProductTransactionsFoundLocally -> {
            messageToShow(getString(R.string.not_found_product_transactions_details), false )
        }
        ProductDetailsViewModel.UiModel.ErrorUpdatingProductDetails -> {
            messageToShow(getString(R.string.not_get_products_details), true)
        }
        ProductDetailsViewModel.UiModel.ErrorGettingTransactionsByProduct -> {
            messageToShow(getString(R.string.not_get_products_details), true)
        }
        ProductDetailsViewModel.UiModel.NotRateDataFoundLocally -> {
            messageToShow(getString(R.string.not_rate_data_found), true)
        }
    }
}
