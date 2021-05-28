package xit.zubrein.gogocarrier.ui.auth.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import dagger.hilt.android.AndroidEntryPoint
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.Navigator
import xit.zubrein.gogocarrier.Utils.error_toast
import xit.zubrein.gogocarrier.Utils.pref.IntentKeys
import xit.zubrein.gogocarrier.base.BaseActivity
import xit.zubrein.gogocarrier.databinding.ActivityOtpBinding
import xit.zubrein.gogocarrier.model.ModelOTPResponse
import xit.zubrein.gogocarrier.ui.auth.listener.OTPListener

@AndroidEntryPoint
class OtpActivity : BaseActivity<ActivityOtpBinding>(), OTPListener {

    var otp: String = ""
    var msisdn: String = ""
    var otp_token: String = ""
    var counter = 30

    override fun getView() = R.layout.activity_otp

    @SuppressLint("SetTextI18n")
    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        val data = intent.getBundleExtra(IntentKeys.INTENT_KEY)
         otp_token = data?.getString(IntentKeys.OTP_TOKEN)!!
         msisdn = data.getString(IntentKeys.MSISDN)!!

        counterSet()

        binding.txtmsisdn.text = "An OTP has been sent to +88${msisdn}."

        binding.pinView.apply {
            setHideLineWhenFilled(false)
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    otp = s.toString()
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }
        binding.submit.setOnClickListener {
            if (otp.length < 4) {
                error_toast("Please enter valid otp")
            } else {
                authViewModel.otpListener = this
                authViewModel.submitOTP(msisdn, otp_token, otp)
            }
        }

        binding.resend.setOnClickListener {
            loadingBar.showDialog()
            authViewModel.sendOtp(msisdn).observe(this,  {
                if(it.status_code == 200){
                    loadingBar.cancelDialog()
                    otp_token = it.otp_token!!
                    counterSet()
                }else{
                    loadingBar.cancelDialog()
                }
            })
        }

    }

    fun counterSet() {
        object : CountDownTimer(30000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                binding.resend.setText("RESEND ($counter)")
                binding.resend.setClickable(false)
                counter--
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                binding.resend.setText("RESEND")
                binding.resend.setClickable(true)
                counter = 30
            }
        }.start()
    }

    override fun onOTPStarted() {
        loadingBar.showDialog()
    }

    override fun onOTPSuccess(response: LiveData<ModelOTPResponse>) {
        response.observe(this, {
            if (it.status_code == 200) {
                loadingBar.cancelDialog()
                val b = Bundle()
                b.putString(IntentKeys.MSISDN,msisdn)
                Navigator.sharedInstance.backWithBundle(this,
                    RegistrationFormActivity::class.java,IntentKeys.INTENT_KEY,b)
            } else {
                loadingBar.cancelDialog()
                error_toast(it.error_msg)
            }
        })
    }

    override fun onOTPFailure(message: String) {
        loadingBar.cancelDialog()
        error_toast(message)
    }
}