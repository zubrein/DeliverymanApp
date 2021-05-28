package xit.zubrein.gogocarrier.ui.auth.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.Navigator
import xit.zubrein.gogocarrier.Utils.error_toast
import xit.zubrein.gogocarrier.Utils.info_toast
import xit.zubrein.gogocarrier.Utils.pref.IntentKeys
import xit.zubrein.gogocarrier.base.BaseActivity
import xit.zubrein.gogocarrier.databinding.ActivityPhoneNumberBinding

@AndroidEntryPoint
class PhoneNumberActivity : BaseActivity<ActivityPhoneNumberBinding>() {

    override fun getView() = R.layout.activity_phone_number

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {

        binding.sendOtp.setOnClickListener {
            var msisdn = binding.txtMsisdn.text.toString()
            if (msisdn.isEmpty() || msisdn.length < 11) {
                error_toast("Please enter a valid number")
            }else{
                loadingBar.showDialog()
                authViewModel.sendOtp(msisdn).observe(this, Observer {
                    if (it.status_code == 200) {
                        loadingBar.cancelDialog()
                        val b = Bundle()
                        b.putString(IntentKeys.OTP_TOKEN,it.otp_token)
                        b.putString(IntentKeys.MSISDN,msisdn)
                        Navigator.sharedInstance.navigateWithBundle(this,
                            OtpActivity::class.java,IntentKeys.INTENT_KEY,b)

                    } else if(it.status_code == 202){
                        loadingBar.cancelDialog()
                        info_toast("User already registered")
                    }else {
                        loadingBar.cancelDialog()
                        error_toast("otp sent Failed")
                    }
                })
            }
        }

    }

}