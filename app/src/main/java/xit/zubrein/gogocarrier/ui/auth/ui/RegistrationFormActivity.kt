package xit.zubrein.gogocarrier.ui.auth.ui

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.Navigator
import xit.zubrein.gogocarrier.Utils.ValidationUtils
import xit.zubrein.gogocarrier.Utils.getEditTextData
import xit.zubrein.gogocarrier.Utils.info_toast
import xit.zubrein.gogocarrier.Utils.pref.IntentKeys
import xit.zubrein.gogocarrier.base.BaseActivity
import xit.zubrein.gogocarrier.databinding.ActivityRegistrationBinding
import xit.zubrein.gogocarrier.model.ModelUser

@AndroidEntryPoint
class RegistrationFormActivity : BaseActivity<ActivityRegistrationBinding>() {

    private var msisdn = ""
    var referenceName = ""

    override fun getView() = R.layout.activity_registration

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {

        val data = intent.getBundleExtra(IntentKeys.INTENT_KEY)
        msisdn = data?.getString(IntentKeys.MSISDN)!!
        Log.d(TAG, "onViewReady: $msisdn")

        binding.next.setOnClickListener {

            binding.apply {
                val name = getEditTextData(etName)
                val email = getEditTextData(etEmail)
                val area = getEditTextData(etArea)
                val password = getEditTextData(etPassword)
                val conPassword = getEditTextData(etConPassword)
                referenceName = getEditTextData(etReferenceName)

                if (name.isEmpty()) {
                    info_toast("Enter your name")
                } else if (area.isEmpty()) {
                    info_toast("Enter a valid address")
                } else if (email.isEmpty()) {
                    info_toast("Enter a valid email address")
                } else if (!ValidationUtils.isValidEmail(email)) {
                    info_toast("Enter a valid email address")
                } else if (password.isEmpty()) {
                    info_toast("Enter your password")
                } else if (conPassword.isEmpty()) {
                    info_toast("Enter your password")
                } else if (password != conPassword) {
                    info_toast("Password not matched")
                } else {
                    val modelUser = ModelUser(
                        name,
                        msisdn,
                        conPassword,
                        referenceName,
                        email,
                        area
                    )

                    val b = Bundle()
                    b.putParcelable(IntentKeys.USER_INFO, modelUser as Parcelable)
                    Navigator.sharedInstance.navigateWithBundle(
                        this@RegistrationFormActivity,
                        TakeProfileImageActivity::class.java,
                        IntentKeys.INTENT_USER,
                        b
                    )
                }

            }


        }

    }
}