package xit.zubrein.gogocarrier.ui.profile

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_profile.*
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.DeliveryAreaUtils
import xit.zubrein.gogocarrier.Utils.Navigator
import xit.zubrein.gogocarrier.Utils.pref.IntentKeys
import xit.zubrein.gogocarrier.Utils.pref.PrefKeys
import xit.zubrein.gogocarrier.Utils.success_toast
import xit.zubrein.gogocarrier.adapter.SelectedAreaAdapter
import xit.zubrein.gogocarrier.base.BaseActivity
import xit.zubrein.gogocarrier.databinding.ActivityProfileBinding
import xit.zubrein.gogocarrier.model.ModelArea
import xit.zubrein.gogocarrier.model.ModelAreaIDList
import xit.zubrein.gogocarrier.model.ModelAreaList
import xit.zubrein.gogocarrier.model.ModelBasicResponse
import xit.zubrein.gogocarrier.ui.profile.listener.OnItemClickListener
import xit.zubrein.gogocarrier.ui.profile.listener.UpdateAreaListener

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>(),OnItemClickListener,
    UpdateAreaListener {

    private lateinit var list : List<ModelArea>

    override fun getView() = R.layout.activity_profile

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {

        binding.back.setOnClickListener {
            finish()
        }
        binding.name.text = pref.getString(PrefKeys.NAME)
        binding.phoneNumber.text = pref.getString(PrefKeys.MSISDN)
        binding.email.text = if(pref.getString(PrefKeys.EMAIL) == "null") "Not set yet" else pref.getString(PrefKeys.EMAIL)
        binding.address.text = if(pref.getString(PrefKeys.ADDRESS) == "null") "Not set yet" else pref.getString(PrefKeys.ADDRESS)

        Glide.with(this)
            .load(pref.getString(PrefKeys.USER_IMAGE))
            .centerCrop()
            .into(binding.image)

        setList()

        binding.addDeliveryArea.setOnClickListener {
            val b = Bundle()
            val model = ModelAreaList(list)
            b.putParcelable(IntentKeys.SELECTED_AREA,model as Parcelable)
            Navigator.sharedInstance.navigateWithBundle(this,SelectDeliveryAreaActivity::class.java,IntentKeys.INTENT_KEY,b)
        }

    }

    private fun setList(){
        profileViewModel.selectedareaList.observe(this,{
            if(it.isNotEmpty()){
                progressBar.visibility = View.GONE
                list = it
                val adapter = SelectedAreaAdapter(this)
                binding.recyclerview.adapter = adapter
                binding.recyclerview.setHasFixedSize(true)
                adapter.addItems(it)
                binding.noItemText.visibility = View.GONE
            }else{
                progressBar.visibility = View.GONE
                list = it
                binding.noItemText.visibility = View.VISIBLE
            }
        })
    }

    override fun onItemClick(model: ModelArea) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert!!")
        builder.setMessage("Are you sure, you want to remove ${model.name} from your list?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes"){dialogInterface, which ->
            val final_list = DeliveryAreaUtils.deleteFromLIst(list,model)
            Log.d(TAG, "onItemClick: ${final_list.toString()}")
            loadingBar.showDialog()
            val newModel = ModelAreaIDList(final_list)
            profileViewModel.updateAreaListener = this
            profileViewModel.update_area(newModel)
        }
        builder.setNegativeButton("cancel"){dialogInterface, which ->
            
        }
        val alertDialog: AlertDialog = builder.create()

        alertDialog.setCancelable(false)
        alertDialog.show()

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
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: called")
        setList()
    }

}