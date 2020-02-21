package com.macdonald.angel.gnb.ui.features.productDetails.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.macdonald.angel.data.model.CurrencyType
import com.macdonald.angel.data.model.TransactionDetailsModel
import com.macdonald.angel.gnb.R
import com.macdonald.angel.gnb.common.round
import kotlinx.android.synthetic.main.item_transaction_detail.view.*

class ProductTransactionsDetailListAdapter(
    val context: Context,
    private val listTransaction: List<TransactionDetailsModel>,
    private val clickListener: (TransactionDetailsModel, View) -> Unit
) : RecyclerView.Adapter<ProductTransactionsDetailListAdapter.ProductTransactionsDetailItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductTransactionsDetailItemViewHolder {
        val inflatedView =
            LayoutInflater.from(context).inflate(R.layout.item_transaction_detail, parent, false)

        return ProductTransactionsDetailItemViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int = listTransaction.size

    override fun onBindViewHolder(holder: ProductTransactionsDetailItemViewHolder, position: Int) {
        holder.bindProductItem(listTransaction[position], clickListener)
    }

    //VIEW HOLDER
    class ProductTransactionsDetailItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView

        fun bindProductItem(
            transactionItem: TransactionDetailsModel,
            clickListener: (TransactionDetailsModel, View) -> Unit
        ) {
            itemView.tvProductNameDetail.text = transactionItem.product
            itemView.tvAmountDetail.text = transactionItem.amount.round()
            itemView.tvCurrencyDetail.text = transactionItem.currency
            itemView.tvAmountChosenCurrencyDetail.text =
                transactionItem.conversionToChosenCurrency.round()
            itemView.tvChosenCurrencyDetail.text = CurrencyType.EUR.currency
            itemView.setOnClickListener {
                clickListener(transactionItem, view)
            }
        }
    }
}
