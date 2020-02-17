package com.macdonald.angel.gnb.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.macdonald.angel.gnb.R
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class TransactionsListFragment : Fragment(), TransactionsListContract.View {

    private val viewModel: TransactionsListViewModel by currentScope.viewModel(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transactions_list, container, false)
    }


    override fun initializeViews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUi(model: TransactionsListViewModel.UiModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
