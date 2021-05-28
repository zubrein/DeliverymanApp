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
import dagger.hilt.android.AndroidEntryPoint
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.*
import xit.zubrein.gogocarrier.Utils.pref.IntentKeys
import xit.zubrein.gogocarrier.base.BaseActivity
import xit.zubrein.gogocarrier.databinding.ActivityNidFrontBinding
import xit.zubrein.gogocarrier.model.ModelUser

@AndroidEntryPoint
class NidFrontActivity : BaseActivity<ActivityNidFrontBinding>() {

    private var filePath: String? = null
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>

    override fun getView() = R.layout.activity_nid_front

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {

        val data = intent.getBundleExtra(IntentKeys.INTENT_USER)
        val userData : ModelUser? = data?.getParcelable(IntentKeys.USER_INFO)
        val user_image_file_path  = data?.getString(IntentKeys.USER_IMAGE_FILE_PATH)

        Log.d(TAG, "onViewReady: $user_image_file_path")

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
            b.putString(IntentKeys.USER_IMAGE_FILE_PATH,user_image_file_path)
            b.putString(IntentKeys.USER_IMAGE_NID_FRONT,filePath)
            Navigator.sharedInstance.navigateWithBundle(this,
                NidBackActivity::class.java,IntentKeys.INTENT_USER,b)

        }

    }

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setOutputCompressQuality(35)
                .getIntent(this@NidFrontActivity)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }

    }


}