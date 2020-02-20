package com.macdonald.angel.gnb.ui.features.productDetails


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.macdonald.angel.gnb.R
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ProductDetailsFragment : Fragment(), ProductDetailsContract.View {

    private val viewModel: ProductDetailsViewModel by currentScope.viewModel(this)

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUi(model: ProductDetailsViewModel.UiModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun canNotGetAnyData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
