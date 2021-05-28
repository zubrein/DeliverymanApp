package xit.zubrein.gogocarrier.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dashboard_filter_layout.view.*
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.error_toast
import xit.zubrein.gogocarrier.Utils.hide
import xit.zubrein.gogocarrier.Utils.visible
import xit.zubrein.gogocarrier.base.BaseActivity
import xit.zubrein.gogocarrier.databinding.ActivityDashboardBinding
import xit.zubrein.gogocarrier.model.ModelDashboard
import xit.zubrein.gogocarrier.ui.dashboard.listener.DashboardListener

@AndroidEntryPoint
class DashboardActivity : BaseActivity<ActivityDashboardBinding>(), DashboardListener {

    override fun getView() = R.layout.activity_dashboard

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {

        getDashboard("today")

        binding.back.setOnClickListener {
            finish()
        }

        binding.filter.setOnClickListener {
            showDialog()
        }

    }

    fun getDashboard(filter: String) {
        dashboardViewModel.dashboardListener = this
        dashboardViewModel.getDashboard(filter)
    }

    override fun getDashboardStarted() {
        visible(binding.progressBar)
    }

    override fun getDashboardSuccess(model: LiveData<ModelDashboard>) {
        model.observe(this, {
            hide(binding.progressBar)
            binding.model = it
        })
    }

    override fun getDashboardFailed(message: String) {
        error_toast(message)
    }

    fun showDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dashboard_filter_layout, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(view)
            .setTitle("Filter")
        val mAlertDialog = mBuilder.show()

        when(pref.getInt("d")){
            1 -> {
                visible(view.today_select_button)
            }
            2-> {
                visible(view.this_week_select_button)
            }
            3-> {
                visible(view.this_month_select_button)
            }
            4-> {
                visible(view.all_select_button)
            }
            else -> {
                visible(view.today_select_button)
            }
        }

        view.today.setOnClickListener {
            mAlertDialog.hide()
            pref.putInt("d",1)
            getDashboard("today")
        }

        view.this_week.setOnClickListener {
            mAlertDialog.hide()
            pref.putInt("d",2)
            getDashboard("this_week")
        }

        view.this_month.setOnClickListener {
            mAlertDialog.hide()
            pref.putInt("d",3)
            getDashboard("this_month")
        }

        view.all.setOnClickListener {
            mAlertDialog.hide()
            pref.putInt("d",4)
            getDashboard("all")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        pref.putInt("d",0)
    }
}