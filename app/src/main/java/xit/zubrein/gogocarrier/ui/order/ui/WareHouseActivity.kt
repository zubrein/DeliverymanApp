package xit.zubrein.gogocarrier.ui.order.ui

import android.content.Intent
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.adapter.AllOrderWarehouseAdapter
import xit.zubrein.gogocarrier.adapter.TodaysOrderWarehouseAdapter
import xit.zubrein.gogocarrier.base.BaseActivity
import xit.zubrein.gogocarrier.databinding.ActivityWareHouseBinding
import xit.zubrein.gogocarrier.model.ModelAllOrder
import xit.zubrein.gogocarrier.model.ModelSingleOrder

@AndroidEntryPoint
class WareHouseActivity : BaseActivity<ActivityWareHouseBinding>() {

    private lateinit var modelAll: ModelAllOrder.Order.Product
    private lateinit var modelToday: ModelSingleOrder.Product

    override fun getView() = R.layout.activity_ware_house

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {

        val data = intent.getBundleExtra("intent")
        if(data?.getString("from") == "all"){
            modelAll = data.getParcelable("data")!!
            val adapterWarehouse = AllOrderWarehouseAdapter(this)
            binding.recyclerViewWarehouse.setHasFixedSize(true)
            binding.recyclerViewWarehouse.adapter = adapterWarehouse
            adapterWarehouse.addItems(modelAll.warehouse)
        }else{
            modelToday = data?.getParcelable("data")!!
            val adapterWarehouse = TodaysOrderWarehouseAdapter(this)
            binding.recyclerViewWarehouse.setHasFixedSize(true)
            binding.recyclerViewWarehouse.adapter = adapterWarehouse
            adapterWarehouse.addItems(modelToday.warehouse)
        }


        binding.back.setOnClickListener {
            finish()
        }



    }
}