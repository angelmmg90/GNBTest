package com.macdonald.angel.gnb.ui.features.rateList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.macdonald.angel.data.model.RateModel
import com.macdonald.angel.gnb.R
import com.macdonald.angel.gnb.ui.features.rateList.adapters.RatesListAdapter
import es.dmoral.toasty.Toasty
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rate_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.model.observe(this, Observer(::updateUi))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        viewModel.getAllRates()
    }


    override fun initializeViews() {
        rvRates.layoutManager = LinearLayoutManager(context!!)
    }

    override fun updateUi(model: RateListViewModel.UiModel) = when(model) {
        is RateListViewModel.UiModel.ShowRates -> {
            listRates = model.rateList as ArrayList<RateModel>
            adapter = RatesListAdapter(
                context!!,
                listRates
            ) { rateItem: RateModel, _: View ->

                val toast = Toasty.info(
                    context!!,
                    "Item clicked",
                    Toast.LENGTH_LONG,
                    true
                )
                toast.show()
            }
            rvRates.adapter = adapter
        }
        RateListViewModel.UiModel.Forbbiden -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.not_get_rates),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()
        }
        RateListViewModel.UiModel.ErrorGettingRates -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.not_get_rates),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()
        }
        RateListViewModel.UiModel.NetWorkError -> {
            val toast = Toasty.info(
                context!!,
                getString(R.string.not_get_rates),
                Toast.LENGTH_LONG,
                true
            )
            toast.show()
        }
    }

}
