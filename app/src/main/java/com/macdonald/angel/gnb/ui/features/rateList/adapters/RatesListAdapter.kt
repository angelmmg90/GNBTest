package com.macdonald.angel.gnb.ui.features.rateList.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.macdonald.angel.data.model.RateModel
import com.macdonald.angel.gnb.R
import com.macdonald.angel.gnb.common.round
import kotlinx.android.synthetic.main.item_rate.view.*

class RatesListAdapter(
    val context: Context,
    private val listRates: List<RateModel>,
    private val clickListener: (RateModel, View) -> Unit
) : RecyclerView.Adapter<RatesListAdapter.RateItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RateItemViewHolder {
        val inflatedView =
            LayoutInflater.from(context).inflate(R.layout.item_rate, parent, false)

        return RateItemViewHolder(
            inflatedView
        )
    }

    override fun getItemCount(): Int = listRates.size

    override fun onBindViewHolder(holder: RateItemViewHolder, position: Int) {
        holder.bindRateItem(listRates[position], clickListener)
    }

    //VIEW HOLDER
    class RateItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView

        fun bindRateItem(
            rateItem: RateModel,
            clickListener: (RateModel, View) -> Unit
        ) {
            itemView.tvFrom.text = rateItem.from
            itemView.tvTo.text = rateItem.to
            itemView.tvRate.text = rateItem.rateChosenCurrency.round()
            itemView.setOnClickListener {
                clickListener(rateItem, view)
            }
        }
    }
}
