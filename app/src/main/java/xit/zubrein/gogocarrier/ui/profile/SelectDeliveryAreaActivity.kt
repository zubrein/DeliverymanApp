package xit.zubrein.gogocarrier.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_select_delivery_area.*
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.*
import xit.zubrein.gogocarrier.Utils.pref.IntentKeys
import xit.zubrein.gogocarrier.adapter.AddDeliveryAreaAdapter
import xit.zubrein.gogocarrier.base.BaseActivity
import xit.zubrein.gogocarrier.databinding.ActivitySelectDeliveryAreaBinding
import xit.zubrein.gogocarrier.model.ModelArea
import xit.zubrein.gogocarrier.model.ModelAreaIDList
import xit.zubrein.gogocarrier.model.ModelAreaList
import xit.zubrein.gogocarrier.model.ModelBasicResponse
import xit.zubrein.gogocarrier.ui.profile.listener.OnItemClickListener
import xit.zubrein.gogocarrier.ui.profile.listener.UpdateAreaListener

@AndroidEntryPoint
class SelectDeliveryAreaActivity : BaseActivity<ActivitySelectDeliveryAreaBinding>(),UpdateAreaListener,
    OnItemClickListener {
    
    private lateinit var list : List<ModelArea>
    private lateinit var selectedListIntent : ModelAreaList

    override fun getView() = R.layout.activity_select_delivery_area

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {

        binding.back.setOnClickListener {
            finish()
        }
        
        val data = intent.getBundleExtra(IntentKeys.INTENT_KEY)
        selectedListIntent = data?.getParcelable(IntentKeys.SELECTED_AREA)!!

        profileViewModel.areaList.observe(this, Observer {
            if(it.isNotEmpty()){
                progressBar.visibility = View.GONE
                val adapter = AddDeliveryAreaAdapter(this)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.setHasFixedSize(true)
                adapter.addItems(it)
                list = it
            }else{
                progressBar.visibility = View.GONE
                info_toast("No area found")
            }
        })



    }

    override fun onItemClick(model: ModelArea) {
        val final_list = DeliveryAreaUtils.addToLIst(selectedListIntent.area_id,model)
        Log.d(TAG, "onItemClick: ${final_list.toString()}")
        val model = ModelAreaIDList(final_list)
        loadingBar.showDialog()
        profileViewModel.updateAreaListener = this
        profileViewModel.update_area(model)
    }

    override fun updateAreaSuccess(model: LiveData<ModelBasicResponse>) {

        model.observe(this,{
            if(it.code == 200){
                loadingBar.cancelDialog()
                success_toast(it.message!!)
                Navigator.sharedInstance.navigate(this,ProfileActivity::class.java)
            }

        })
    }

    override fun updateAreaFailed(message: String) {
        loadingBar.cancelDialog()
        error_toast(message)
    }

}