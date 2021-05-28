package xit.zubrein.gogocarrier.adapter

import android.content.Context
import android.os.Parcelable
import dagger.hilt.android.AndroidEntryPoint
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.Navigator
import xit.zubrein.gogocarrier.Utils.info_toast
import xit.zubrein.gogocarrier.base.BaseRecyclerviewAdapter
import xit.zubrein.gogocarrier.databinding.AllOrderProductItemBinding
import xit.zubrein.gogocarrier.databinding.SelectedAreaItemBinding
import xit.zubrein.gogocarrier.model.ModelAllOrder
import xit.zubrein.gogocarrier.model.ModelArea
import xit.zubrein.gogocarrier.ui.order.ui.AllOrderDetailsActivity
import xit.zubrein.gogocarrier.ui.order.ui.WareHouseActivity
import xit.zubrein.gogocarrier.ui.profile.listener.OnItemClickListener


class SelectedAreaAdapter(private val context: Context) :
    BaseRecyclerviewAdapter<ModelArea, SelectedAreaItemBinding>() {

    override fun getLayout() = R.layout.selected_area_item

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<SelectedAreaItemBinding>,
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