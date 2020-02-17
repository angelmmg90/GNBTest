package com.macdonald.angel.gnb.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.macdonald.angel.data.model.TransactionModel
import com.macdonald.angel.gnb.R
import kotlinx.android.synthetic.main.item_transaction.view.*

class TransactionsListAdapter(
    val context: Context,
    private val listTransactions: List<TransactionModel>,
    private val clickListener: (TransactionModel, View) -> Unit
    ) : RecyclerView.Adapter<TransactionsListAdapter.TransactionItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionItemViewHolder {
        val inflatedView =
            LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false)

        return TransactionItemViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int = listTransactions.size

    override fun onBindViewHolder(holder: TransactionItemViewHolder, position: Int) {
        holder.bindTransactionItem(listTransactions[position], clickListener)
    }

    //VIEW HOLDER
    class TransactionItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView

        fun bindTransactionItem(
           transactionItem: TransactionModel,
            clickListener: (TransactionModel, View) -> Unit
        ) {
            itemView.tvProduct.text = transactionItem.product
            itemView.tvAmount.text = transactionItem.amount.toString()
            itemView.tvCurrency.text = transactionItem.currency
            itemView.setOnClickListener {
                clickListener(transactionItem, view)
            }
        }
    }
}
