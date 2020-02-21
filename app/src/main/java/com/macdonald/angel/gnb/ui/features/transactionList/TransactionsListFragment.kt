package com.macdonald.angel.gnb.ui.features.transactionList


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.gnb.R
import com.macdonald.angel.gnb.common.messageToShow
import com.macdonald.angel.gnb.ui.features.transactionList.adapters.TransactionsListAdapter
import kotlinx.android.synthetic.main.fragment_transactions_list.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class TransactionsListFragment : Fragment(),
    TransactionsListContract.View {

    private val viewModel: TransactionsListViewModel by currentScope.viewModel(this)

    private lateinit var adapter: TransactionsListAdapter
    private lateinit var listTransactions: ArrayList<TransactionModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transactions_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.model.observe(this, Observer(::updateUi))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        viewModel.getAllTransactionsFromLocal()
    }

    override fun initializeViews() {
        rvTransactions.layoutManager = LinearLayoutManager(context!!)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_info_conversion_rates, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.infoConversionRates -> {
                goToRateInfo()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun goToRateInfo() {
        val conversionRateInfoScreen =
            TransactionsListFragmentDirections.goConversionRateInfoAction()
        findNavController().navigate(conversionRateInfoScreen)
    }

    override fun updateUi(model: TransactionsListViewModel.UiModel) = when (model) {
        is TransactionsListViewModel.UiModel.ShowTransactions -> {
            listTransactions = model.transactionList as ArrayList<TransactionModel>
            adapter = TransactionsListAdapter(
                context!!,
                listTransactions
            ) { transactionItem: TransactionModel, _: View ->
                messageToShow(
                    transactionItem.amount.toString() + " " +
                            transactionItem.currency, false
                )
            }
            rvTransactions.adapter = adapter
        }
        TransactionsListViewModel.UiModel.ErrorGettingTrasactions -> {
            messageToShow(getString(R.string.not_get_transactions), true)
        }
        TransactionsListViewModel.UiModel.NetWorkError -> {
            messageToShow(getString(R.string.network_error), true)
        }
        TransactionsListViewModel.UiModel.NotTransactionDataFoundLocally -> {
            viewModel.getAllTransactionsFromRemote()
        }
        TransactionsListViewModel.UiModel.ErrorGettingsTransactions -> {
            messageToShow(getString(R.string.not_get_transactions), true)
        }
    }
}
