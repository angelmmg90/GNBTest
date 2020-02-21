package com.macdonald.angel.gnb.ui.features.rateList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.macdonald.angel.data.model.RateModel
import com.macdonald.angel.gnb.R
import com.macdonald.angel.gnb.common.hide
import com.macdonald.angel.gnb.common.messageToShow
import com.macdonald.angel.gnb.common.visible
import com.macdonald.angel.gnb.ui.features.rateList.adapters.RatesListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_rate_list.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class RateListFragment : Fragment(), RateListContract.View {

    private val viewModel: RateListViewModel by currentScope.viewModel(this)

    private lateinit var adapter: RatesListAdapter
    private lateinit var listRates: ArrayList<RateModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rate_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.model.observe(this, Observer(::updateUi))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.requireActivity().progressBar.visible()
        initializeViews()
        viewModel.getAllRatesFromLocal()
    }


    override fun initializeViews() {
        rvRates.layoutManager = LinearLayoutManager(context!!)
    }

    override fun updateUi(model: RateListViewModel.UiModel) = when (model) {
        is RateListViewModel.UiModel.ShowRates -> {
            listRates = model.rateList as ArrayList<RateModel>
            adapter = RatesListAdapter(
                context!!,
                listRates
            ) { rateItem: RateModel, _: View ->
                messageToShow(
                    rateItem.from + "->" +
                            rateItem.to + ": " +
                            rateItem.rate.toString(), false
                )
            }
            rvRates.adapter = adapter
            this.requireActivity().progressBar.hide()
        }
        RateListViewModel.UiModel.ErrorGettingRates -> {
            messageToShow(getString(R.string.not_get_rates), true)
            this.requireActivity().progressBar.hide()
        }
        RateListViewModel.UiModel.NetWorkError -> {
            messageToShow(getString(R.string.network_error), true)
            this.requireActivity().progressBar.hide()
        }
        RateListViewModel.UiModel.NotRateDataFoundLocally -> {
            viewModel.getAllRatesFromRemote()
        }
    }
}
