package com.macdonald.angel.gnb.ui.features.rateList


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.macdonald.angel.data.model.RateModel
import com.macdonald.angel.data.model.TransactionModel

import com.macdonald.angel.gnb.R
import com.macdonald.angel.gnb.ui.features.transactionList.adapters.TransactionsListAdapter
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class RateListFragment : Fragment(), RateListContract.View {

    private val viewModel: RateListViewModel by currentScope.viewModel(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rate_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.model.observe(this, Observer(::updateUi))
    }


    override fun initializeViews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUi(model: RateListViewModel.UiModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
