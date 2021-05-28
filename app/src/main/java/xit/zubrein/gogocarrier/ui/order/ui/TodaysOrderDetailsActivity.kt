package xit.zubrein.gogocarrier.ui.order.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import dagger.hilt.android.AndroidEntryPoint
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.error_toast
import xit.zubrein.gogocarrier.Utils.info_toast
import xit.zubrein.gogocarrier.Utils.success_toast
import xit.zubrein.gogocarrier.adapter.TodaysOrderProductAdapter
import xit.zubrein.gogocarrier.base.BaseActivity
import xit.zubrein.gogocarrier.databinding.ActivityTodaysOrderDetailsBinding
import xit.zubrein.gogocarrier.model.ModelBasicResponse
import xit.zubrein.gogocarrier.model.ModelSingleOrder
import xit.zubrein.gogocarrier.ui.order.listener.ProductPickListener
import java.util.*

@AndroidEntryPoint
class TodaysOrderDetailsActivity : BaseActivity<ActivityTodaysOrderDetailsBinding>(),ProductPickListener {

    override fun getView() = R.layout.activity_todays_order_details

    private lateinit var modelSingleOrder: ModelSingleOrder

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {

        val data = intent.getBundleExtra("intent")
        modelSingleOrder = data?.getParcelable("data")!!
        binding.model = modelSingleOrder

        binding.back.setOnClickListener {
            finish()
        }

        val adapter = TodaysOrderProductAdapter(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
        adapter.addItems(modelSingleOrder.product)

        if(modelSingleOrder.status == "pending"){
            binding.productCollected.visibility = View.VISIBLE
            binding.deliveredText.visibility = View.GONE
            binding.userConfirmation.visibility = View.GONE
        }else if(modelSingleOrder.status == "picked"){
            binding.productCollected.visibility = View.GONE
            binding.deliveredText.visibility = View.GONE
            binding.userConfirmation.visibility = View.VISIBLE
        }else if(modelSingleOrder.status == "delivered"){
            binding.productCollected.visibility = View.GONE
            binding.deliveredText.visibility = View.VISIBLE
            binding.userConfirmation.visibility = View.GONE
        }

        binding.productCollected.setOnClickListener {
            orderViewModel.productPickListener = this
            orderViewModel.productPicked(modelSingleOrder.order_no)
        }

        binding.sendRemarks.setOnClickListener {
            orderViewModel.productPickListener = this
            val stMessage = binding.etMessage.text.toString()
            if (stMessage.isEmpty()) {
                info_toast("Field must not empty")
            } else {
                orderViewModel.sendRemarks(
                    modelSingleOrder.order_no,
                    stMessage
                )
            }
        }

    }

    override fun productPickedStarted() {
        loadingBar.showDialog()
    }

    override fun productPickedSuccess(model: LiveData<ModelBasicResponse>) {
        model.observe(this,{
            if(it.status_code == 200){
                loadingBar.cancelDialog()
                success_toast("Product Picked Successfully")
                binding.productCollected.visibility = View.GONE
                binding.deliveredText.visibility = View.GONE
                binding.userConfirmation.visibility = View.VISIBLE
            }
        })
    }

    override fun productPickedFailed(message: String) {
        loadingBar.cancelDialog()
        error_toast(message)
    }

    override fun sendRemarkStarted() {
        loadingBar.showDialog()
    }

    override fun sendRemarkSuccess(model: LiveData<ModelBasicResponse>) {
        model.observe(this, {
            if (it.status_code == 200) {
                loadingBar.cancelDialog()
                success_toast("Remarks sent Successfully")
                binding.etMessage.setText("")
            }
        })
    }

    override fun sendRemarkFailed(message: String) {
        loadingBar.cancelDialog()
        error_toast(message)
    }
}