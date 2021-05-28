package xit.zubrein.gogocarrier.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.DateFormatUtils
import xit.zubrein.gogocarrier.Utils.Navigator
import xit.zubrein.gogocarrier.base.BaseRecyclerviewAdapter
import xit.zubrein.gogocarrier.databinding.OrderItemBinding
import xit.zubrein.gogocarrier.model.ModelSingleOrder
import xit.zubrein.gogocarrier.ui.order.ui.TodaysOrderDetailsActivity

class TodaysOrderAdapter(private val context:Context) : BaseRecyclerviewAdapter<ModelSingleOrder,OrderItemBinding>(){

    override fun getLayout() = R.layout.order_item

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Companion.BaseViewHolder<OrderItemBinding>, position: Int) {
        holder.binding.apply {
            val item = items[position]
            model = item
            val timeArray = DateFormatUtils.shared.getTime(item.date)
            time.text = "${timeArray[0]} : ${timeArray[1]} ${timeArray[2]}"
            itemPosition.text = "${position+1}"

            if(item.status == "delivered"){
                orderStatus.setTextColor(context.resources.getColor(R.color.colorPrimary))
            }else if(item.status == "picked"){
                orderStatus.setTextColor(context.resources.getColor(R.color.orange))
            }else if(item.status == "pending"){
                orderStatus.setTextColor(context.resources.getColor(R.color.blue))
            }

            parent.setOnClickListener {
                val b = bundle
                b.putParcelable("data", item as Parcelable)
                Navigator.sharedInstance.navigateWithBundle(context, TodaysOrderDetailsActivity::class.java,"intent",b)
            }
        }
    }

}