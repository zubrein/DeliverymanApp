package xit.zubrein.gogocarrier.ui.order.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_all_order.*
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.adapter.AllOrderParentAdapter
import xit.zubrein.gogocarrier.base.BaseActivity
import xit.zubrein.gogocarrier.databinding.ActivityAllOrderBinding
import xit.zubrein.gogocarrier.model.ModelAllOrder
import xit.zubrein.gogocarrier.ui.order.listener.AllOrderListListener

@AndroidEntryPoint
class AllOrderActivity : BaseActivity<ActivityAllOrderBinding>(), AllOrderListListener {

    override fun getView() = R.layout.activity_all_order

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {


        binding.back.setOnClickListener {
            finish()
        }

        orderViewModel.allOrderListListener = this
        orderViewModel.getAllOrderList()

    }

    override fun allOrderReceivedStarted() {
        progressBar.visibility = View.VISIBLE
    }

    override fun allOrderReceivedSuccess(all_order_list: LiveData<List<ModelAllOrder>>) {
        val adapter = AllOrderParentAdapter(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
        all_order_list.observe(this,{
            progressBar.visibility = View.GONE
            adapter.addItems(it)

            if(it.size == 0){
                binding.noOrdersLayout.visibility = View.VISIBLE
            }else{
                binding.noOrdersLayout.visibility = View.GONE
            }
        })
    }

    override fun allOrderReceivedFailed(message: String) {
        progressBar.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        orderViewModel.allOrderListListener = this
        orderViewModel.getAllOrderList()
    }
}