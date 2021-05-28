package xit.zubrein.gogocarrier.adapter

import android.annotation.SuppressLint
import android.content.Context
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.DateFormatUtils
import xit.zubrein.gogocarrier.base.BaseRecyclerviewAdapter
import xit.zubrein.gogocarrier.databinding.AllOrderParentItemBinding
import xit.zubrein.gogocarrier.model.ModelAllOrder

class AllOrderParentAdapter (private val context: Context) : BaseRecyclerviewAdapter<ModelAllOrder, AllOrderParentItemBinding>(){

    override fun getLayout() = R.layout.all_order_parent_item

    override fun onBindViewHolder(holder: Companion.BaseViewHolder<AllOrderParentItemBinding>, position: Int) {
        holder.binding.apply {
            val item = items[position]
            val cDate = DateFormatUtils.shared.getDateAllOrder(item.date)
            date.text = "${cDate[2]} ${cDate[1]}, ${cDate[0]}"
            val adapter = AllOrderChildAdapter(context)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
            adapter.addItems(item.order)
        }
    }

}