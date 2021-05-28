package xit.zubrein.gogocarrier.adapter

import android.content.Context
import android.os.Parcelable
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.Navigator
import xit.zubrein.gogocarrier.base.BaseRecyclerviewAdapter
import xit.zubrein.gogocarrier.databinding.AllOrderProductItemBinding
import xit.zubrein.gogocarrier.model.ModelAllOrder
import xit.zubrein.gogocarrier.ui.order.ui.AllOrderDetailsActivity
import xit.zubrein.gogocarrier.ui.order.ui.WareHouseActivity

class AllOrderProductAdapter(private val context: Context) :
    BaseRecyclerviewAdapter<ModelAllOrder.Order.Product, AllOrderProductItemBinding>() {

    override fun getLayout() = R.layout.all_order_product_item

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<AllOrderProductItemBinding>,
        position: Int
    ) {
        holder.binding.apply {
            val item = items[position]
            model = item

            parent.setOnClickListener {
                val b = bundle
                b.putParcelable("data", item as Parcelable)
                b.putString("from","all")
                Navigator.sharedInstance.navigateWithBundle(context, WareHouseActivity::class.java,"intent",b)
            }

        }
    }

}