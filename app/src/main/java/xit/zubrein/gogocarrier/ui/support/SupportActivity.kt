package xit.zubrein.gogocarrier.ui.support

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.base.BaseActivity
import xit.zubrein.gogocarrier.databinding.ActivitySupportBinding

class SupportActivity : BaseActivity<ActivitySupportBinding>() {
    override fun getView() = R.layout.activity_support

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {

        supportActionBar?.let {
            supportActionBar!!.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
            it.title = "Support center"
        }

        binding.callNow.setOnClickListener {
            startActivity(Intent("android.intent.action.DIAL", Uri.parse("tel:01000000000")))
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.let {
            if (item.itemId == android.R.id.home) {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}