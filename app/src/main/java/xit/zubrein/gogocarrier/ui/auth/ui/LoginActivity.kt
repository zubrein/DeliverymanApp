package xit.zubrein.gogocarrier.ui.auth.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.*
import xit.zubrein.gogocarrier.Utils.pref.PrefKeys
import xit.zubrein.gogocarrier.base.BaseActivity
import xit.zubrein.gogocarrier.databinding.ActivityLoginBinding
import xit.zubrein.gogocarrier.model.ModelAuth
import xit.zubrein.gogocarrier.ui.auth.listener.AuthListener
import xit.zubrein.gogocarrier.ui.home.HomeActivity

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(), AuthListener {
    override fun getView() = R.layout.activity_login

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {

        if(pref.getBoolean(PrefKeys.LOGIN_STATUS,false)){
            Navigator.sharedInstance.navigateClear(this, HomeActivity::class.java)
        }

        authViewModel.authListener = this
        binding.btnLogin.setOnClickListener {
            val msisdn = getEditTextData(binding.etMsisdn)
            val password = getEditTextData(binding.etPassword)
            if (!ValidationUtils.isValidPhoneNumber(msisdn)){
                error_toast("Please enter a valid number")
            }else if(password.isEmpty()){
                error_toast("Please enter a valid password")
            }else {
                loadingBar.showDialog()
                authViewModel.getAuthResponse(
                    msisdn, password
                )
            }
        }

        binding.btnRegister.setOnClickListener {
            Navigator.sharedInstance.navigate(this, PhoneNumberActivity::class.java)
        }

    }

    override fun onSuccess(modelAuth: LiveData<ModelAuth>) {

        modelAuth.observe(this, Observer {
            loadingBar.cancelDialog()
            if(it.code == 200){
                pref.putString(PrefKeys.AUTH_TOKEN,it.token!!)
                pref.putString(PrefKeys.NAME,it.user?.name!!)
                pref.putString(PrefKeys.MSISDN,it.user?.contact_no!!)
                pref.putString(PrefKeys.EMAIL,it.user?.email.toString())
                pref.putString(PrefKeys.ADDRESS,it.user?.address.toString())
                pref.putString(PrefKeys.USER_IMAGE,it.user?.user_image.toString())
                pref.putBoolean(PrefKeys.LOGIN_STATUS,true)
                Navigator.sharedInstance.navigateClear(this, HomeActivity::class.java)
            }else if(it.code == 403){
                binding.notApprovedText.visibility = View.VISIBLE
            }else{
                error_toast(it.message)
            }
        })
    }

    override fun onFailure(message: String) {
        loadingBar.cancelDialog()
        error_toast(message)
    }


}