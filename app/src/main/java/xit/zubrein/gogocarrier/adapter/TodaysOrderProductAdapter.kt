package xit.zubrein.gogocarrier.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.DateFormatUtils
import xit.zubrein.gogocarrier.Utils.Navigator
import xit.zubrein.gogocarrier.base.BaseRecyclerviewAdapter
import xit.zubrein.gogocarrier.databinding.AllOrderParentItemBinding
import xit.zubrein.gogocarrier.databinding.TodaysOrderProductItemBinding
import xit.zubrein.gogocarrier.model.ModelAllOrder
import xit.zubrein.gogocarrier.model.ModelSingleOrder
import xit.zubrein.gogocarrier.ui.order.ui.WareHouseActivity

class TodaysOrderProductAdapter (private val context: Context) : BaseRecyclerviewAdapter<ModelSingleOrder.Product, TodaysOrderProductItemBinding>(){

    override fun getLayout() = R.layout.todays_order_product_item

    override fun onBindViewHolder(holder: Companion.BaseViewHolder<TodaysOrderProductItemBinding>, position: Int) {
        holder.binding.apply {
            val item = items[position]
            model = item

            parent.setOnClickListener {
                val b = bundle
                b.putParcelable("data", item as Parcelable)
                b.putString("from","today")
                Navigator.sharedInstance.navigateWithBundle(context, WareHouseActivity::class.java,"intent",b)
            }

        }
    }

}