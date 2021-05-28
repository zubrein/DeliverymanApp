package xit.zubrein.gogocarrier.Utils

import android.app.Activity
import android.app.Dialog
import xit.zubrein.gogocarrier.R
import javax.inject.Inject

class LoadingBar @Inject constructor(activity: Activity) {

    var dialog:Dialog = Dialog(activity)

    fun showDialog() {
        dialog.apply {
            setCancelable(true)
            setContentView(R.layout.loading_dialog)
            show()
        }
    }

    fun cancelDialog(){
        dialog.dismiss()
    }
}