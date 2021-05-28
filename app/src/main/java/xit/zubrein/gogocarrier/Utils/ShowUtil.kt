package xit.zubrein.gogocarrier.Utils

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.toast_error.view.*
import kotlinx.android.synthetic.main.toast_info.view.*
import kotlinx.android.synthetic.main.toast_success.view.*
import xit.zubrein.gogocarrier.R

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.success_toast(message: String) {
    val myToast = Toast(applicationContext)
    myToast.duration = Toast.LENGTH_SHORT
    myToast.setGravity(Gravity.BOTTOM, 0, 150)
    val v: View = View.inflate(this, R.layout.toast_success, null)
    v.success_message.text = message
    myToast.view = v
    myToast.show()
}

fun Context.error_toast(message: String) {
    val myToast = Toast(applicationContext)
    myToast.duration = Toast.LENGTH_SHORT
    myToast.setGravity(Gravity.BOTTOM, 0, 150)
    val v: View = View.inflate(this, R.layout.toast_error, null)
    v.error_message.text = message
    myToast.view = v
    myToast.show()
}

fun Context.info_toast(message: String) {
    val myToast = Toast(applicationContext)
    myToast.duration = Toast.LENGTH_SHORT
    myToast.setGravity(Gravity.BOTTOM, 0, 150)
    val v: View = View.inflate(this, R.layout.toast_info, null)
    v.info_message.text = message
    myToast.view = v
    myToast.show()
}

fun getEditTextData(view: EditText): String {

    return view.text.toString()

}

fun visible(view : View) {
    view.visibility = View.VISIBLE
}

fun hide(view : View) {
    view.visibility = View.GONE
}

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.mipmap.ic_launcher) // change this
        .into(view)

}