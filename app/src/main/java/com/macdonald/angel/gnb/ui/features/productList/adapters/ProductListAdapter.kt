package com.macdonald.angel.gnb.ui.features.productList.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.macdonald.angel.data.model.ProductModel
import com.macdonald.angel.gnb.R
import com.macdonald.angel.gnb.ui.features.productDetails.adapters.ProductTransactionsDetailListAdapter
import kotlinx.android.synthetic.main.item_product.view.*

class ProductListAdapter(
    val context: Context,
    private val listProducts: List<ProductModel>,
    private val clickListener: (ProductModel, View) -> Unit
    ) : RecyclerView.Adapter<ProductListAdapter.ProductItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductItemViewHolder {
        val inflatedView =
            LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)

        return ProductItemViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int = listProducts.size

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        holder.bindProductItem(listProducts[position], clickListener)
    }

    //VIEW HOLDER
    class ProductItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView

        fun bindProductItem(
           productItem: ProductModel,
            clickListener: (ProductModel, View) -> Unit
        ) {
            itemView.tvProductName.text = productItem.name
            itemView.setOnClickListener {
                clickListener(productItem, view)
            }
        }
    }
}
