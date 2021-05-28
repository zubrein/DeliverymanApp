package xit.zubrein.gogocarrier.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.base.BaseRecyclerviewAdapter
import xit.zubrein.gogocarrier.databinding.TodaysOrderWarehouseItemBinding
import xit.zubrein.gogocarrier.model.ModelSingleOrder

class TodaysOrderWarehouseAdapter(private val context: Context) :
    BaseRecyclerviewAdapter<ModelSingleOrder.Product.Warehouse, TodaysOrderWarehouseItemBinding>() {

    override fun getLayout() = R.layout.todays_order_warehouse_item

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<TodaysOrderWarehouseItemBinding>,
        position: Int
    ) {
        holder.binding.apply {
            val item = items[position]
            model = item
            itemPosition.text = "${position+1}"
            callNow.setOnClickListener {
                context.startActivity(Intent("android.intent.action.DIAL", Uri.parse("tel:${item.contact_no}")))
            }
        }
    }

}