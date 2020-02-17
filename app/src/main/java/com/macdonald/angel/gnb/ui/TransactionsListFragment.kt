package com.macdonald.angel.gnb.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.macdonald.angel.data.model.TransactionModel

import com.macdonald.angel.gnb.R
import com.macdonald.angel.gnb.ui.adapters.TransactionsListAdapter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_transactions_list.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class TransactionsListFragment : Fragment(), TransactionsListContract.View {

    private val viewModel: TransactionsListViewModel by currentScope.viewModel(this)

    private lateinit var adapter: TransactionsListAdapter
    private lateinit var listTransactions: ArrayList<TransactionModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        viewModel.getAllTransactions()
    }
    override fun initializeViews() {
        rvTransactions.layoutManager = LinearLayoutManager(context!!)
    }

    override fun updateUi(model: TransactionsListViewModel.UiModel) = when(model) {
        is TransactionsListViewModel.UiModel.ShowTransactions -> {
            listTransactions = model.transactionList as ArrayList<TransactionModel>
            adapter = TransactionsListAdapter(
                context!!,
                listTransactions
            ) { transactionItem: TransactionModel, _: View ->

                val toast = Toasty.info(
                    context!!,
                    "Item clicked",
                    Toast.LENGTH_LONG,
                    true
                )
                toast.show()
            }
            rvTransactions.adapter = adapter
        }
        TransactionsListViewModel.UiModel.Forbbiden -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.not_get_transactions),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()
        }
        TransactionsListViewModel.UiModel.ErrorGettingTrasactions -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.not_get_transactions),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()
        }
        TransactionsListViewModel.UiModel.NetWorkError -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.not_get_transactions),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()
        }
    }


}
