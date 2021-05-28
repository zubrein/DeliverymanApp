package xit.zubrein.gogocarrier.adapter

import android.content.Context
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.base.BaseRecyclerviewAdapter
import xit.zubrein.gogocarrier.databinding.AddDeliveryAreaItemBinding
import xit.zubrein.gogocarrier.model.ModelArea
import xit.zubrein.gogocarrier.ui.profile.listener.OnItemClickListener


class AddDeliveryAreaAdapter(private val context: Context) :
    BaseRecyclerviewAdapter<ModelArea, AddDeliveryAreaItemBinding>() {

    override fun getLayout() = R.layout.add_delivery_area_item

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<AddDeliveryAreaItemBinding>,
        position: Int
    ) {
        holder.binding.apply {
            val item = items[position]
            model = item
            itemClick = context as OnItemClickListener
            executePendingBindings()
        }
    }

}