package xit.zubrein.gogocarrier.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.base.BaseRecyclerviewAdapter
import xit.zubrein.gogocarrier.databinding.AllOrderWarehouseItemBinding
import xit.zubrein.gogocarrier.model.ModelAllOrder

class AllOrderWarehouseAdapter(private val context: Context) :
    BaseRecyclerviewAdapter<ModelAllOrder.Order.Product.Warehouse, AllOrderWarehouseItemBinding>() {

    override fun getLayout() = R.layout.all_order_warehouse_item

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<AllOrderWarehouseItemBinding>,
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