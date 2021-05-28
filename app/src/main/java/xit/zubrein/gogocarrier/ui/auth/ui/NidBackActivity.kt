package xit.zubrein.gogocarrier.ui.auth.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.ImageUploader
import xit.zubrein.gogocarrier.Utils.Navigator
import xit.zubrein.gogocarrier.Utils.error_toast
import xit.zubrein.gogocarrier.Utils.pref.IntentKeys
import xit.zubrein.gogocarrier.Utils.success_toast
import xit.zubrein.gogocarrier.base.BaseActivity
import xit.zubrein.gogocarrier.databinding.ActivityNidBackBinding
import xit.zubrein.gogocarrier.model.ModelBasicResponse
import xit.zubrein.gogocarrier.model.ModelUser
import xit.zubrein.gogocarrier.ui.auth.listener.RegisterListener

@AndroidEntryPoint
class NidBackActivity : BaseActivity<ActivityNidBackBinding>(), RegisterListener {

    private var filePath: String? = null
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>

    override fun getView() = R.layout.activity_nid_back

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {

        val data = intent.getBundleExtra(IntentKeys.INTENT_USER)
        val userData : ModelUser? = data?.getParcelable(IntentKeys.USER_INFO)
        val user_image_file_path  = data?.getString(IntentKeys.USER_IMAGE_FILE_PATH)
        val user_image_nid_front  = data?.getString(IntentKeys.USER_IMAGE_NID_FRONT)

        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract) {
            it?.let {
                binding.imageView.setImageURI(it)
                filePath = it.path
                Log.d("Filepath", "onCreate: $filePath")
                binding.next.visibility = View.VISIBLE
                binding.selectImage.text = "Change image"
            }
        }

        binding.selectImage.setOnClickListener {
            cropActivityResultLauncher.launch(null)
        }

        binding.next.setOnClickListener {
            val desc = ImageUploader.getDescription(userData!!)
            val user_nid_front = ImageUploader.getFilePart(user_image_nid_front!!,"user_nid_front")
            val user_nid_back = ImageUploader.getFilePart(filePath!!,"user_nid_back")
            val user_image = ImageUploader.getFilePart(user_image_file_path!!,"user_image")


            Log.d(TAG, "onViewReadyNIDBACK: $desc \n $user_image_nid_front \n $filePath \n $user_image_file_path")


            authViewModel.registerListener = this
            authViewModel.doRegistration(desc,user_image,user_nid_front,user_nid_back)

        }

    }

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setOutputCompressQuality(35)
                .getIntent(this@NidBackActivity)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }

    }


    override fun onRegisterStarted() {
        loadingBar.showDialog()
        Log.d(TAG, "onRegisterStarted: started")
    }

    override fun onRegisterSuccess(model: LiveData<ModelBasicResponse>) {
        model.observe(this, Observer {
            if(it.code == 200){
                loadingBar.cancelDialog()
                success_toast("Registration successful")
                Navigator.sharedInstance.navigateClear(this, LoginActivity::class.java)
            }else{
                loadingBar.cancelDialog()
                error_toast(it.message!!)
            }
        })
    }

    override fun onRegisterFailure(message: String) {
        loadingBar.cancelDialog()
        error_toast(message)
        Log.d(TAG, "onRegisterFailure: $message. Please try again")
    }

}