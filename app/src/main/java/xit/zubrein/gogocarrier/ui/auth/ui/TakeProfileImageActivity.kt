package xit.zubrein.gogocarrier.ui.auth.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.theartofdev.edmodo.cropper.CropImage
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.Navigator
import xit.zubrein.gogocarrier.Utils.pref.IntentKeys
import xit.zubrein.gogocarrier.base.BaseActivity
import xit.zubrein.gogocarrier.databinding.ActivityTakeProfileImageBinding
import xit.zubrein.gogocarrier.model.ModelUser

class TakeProfileImageActivity : BaseActivity<ActivityTakeProfileImageBinding>() {

    private var filePath: String? = null
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>
    
    override fun getView() = R.layout.activity_take_profile_image

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        
        val data = intent.getBundleExtra(IntentKeys.INTENT_USER)
        val userData : ModelUser? = data?.getParcelable(IntentKeys.USER_INFO)
        Log.d(TAG, "onViewReady: $userData")

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
            val b = Bundle()
            b.putParcelable(IntentKeys.USER_INFO,userData as Parcelable)
            b.putString(IntentKeys.USER_IMAGE_FILE_PATH,filePath)
            Navigator.sharedInstance.navigateWithBundle(this,
                NidFrontActivity::class.java,IntentKeys.INTENT_USER,b)
        }

        
    }

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(16, 16)
                .setOutputCompressQuality(25)
                .getIntent(this@TakeProfileImageActivity)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }

    }

}