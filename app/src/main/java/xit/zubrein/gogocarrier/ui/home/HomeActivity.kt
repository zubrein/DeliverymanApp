package xit.zubrein.gogocarrier.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.layout_nav.*
import xit.zubrein.gogocarrier.R
import xit.zubrein.gogocarrier.Utils.DateFormatUtils
import xit.zubrein.gogocarrier.Utils.Navigator
import xit.zubrein.gogocarrier.Utils.error_toast
import xit.zubrein.gogocarrier.Utils.pref.PrefKeys
import xit.zubrein.gogocarrier.adapter.TodaysOrderAdapter
import xit.zubrein.gogocarrier.base.BaseActivity
import xit.zubrein.gogocarrier.databinding.ActivityHomeBinding
import xit.zubrein.gogocarrier.model.ModelBasicResponse
import xit.zubrein.gogocarrier.model.ModelSingleOrder
import xit.zubrein.gogocarrier.notification.Configuration.Companion.TOPIC_GLOBAL
import xit.zubrein.gogocarrier.ui.auth.ui.LoginActivity
import xit.zubrein.gogocarrier.ui.auth.listener.LogoutListener
import xit.zubrein.gogocarrier.ui.dashboard.DashboardActivity
import xit.zubrein.gogocarrier.ui.order.ui.AllOrderActivity
import xit.zubrein.gogocarrier.ui.order.listener.TodaysOrderListListener
import xit.zubrein.gogocarrier.ui.profile.ProfileActivity
import xit.zubrein.gogocarrier.ui.support.SupportActivity

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(),
    TodaysOrderListListener,
    LogoutListener {

    lateinit var toggle: ActionBarDrawerToggle

    override fun getView() = R.layout.activity_home

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showNavigationDrawer()
        date.text = "Date: " + DateFormatUtils.shared.getCurrentdate()
        name.text = pref.getString(PrefKeys.NAME)
        authViewModel.getWallet().observe(this, Observer {
            wallet.text = "Wallet : ${it.deposit} BDT"
        })


        orderViewModel.todaysOrderListListener = this
        orderViewModel.getTodaysOrderList()

        firebaseToken()

    }

    private fun showNavigationDrawer() {
        toggle = ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navDrawerMenu()
    }

    private fun navDrawerMenu() {
        all_request.setOnClickListener {
            Navigator.sharedInstance.navigate(this, AllOrderActivity::class.java)
            nav_state()
        }
        dashboard.setOnClickListener {
            Navigator.sharedInstance.navigate(this, DashboardActivity::class.java)
            nav_state()
        }
        profile.setOnClickListener {
            Navigator.sharedInstance.navigate(this, ProfileActivity::class.java)
            nav_state()
        }
        logout.setOnClickListener {
            authViewModel.logoutListener = this
            authViewModel.logout()
            nav_state()
        }
        support.setOnClickListener {
            Navigator.sharedInstance.navigate(this, SupportActivity::class.java)
            nav_state()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun nav_state() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    override fun todaysOrderReceivedStarted() {
        progressBar.visibility = View.VISIBLE
    }

    override fun todaysOrderReceivedSuccess(todays_order_list: LiveData<List<ModelSingleOrder>>) {
        todays_order_list.observe(this, {
            progressBar.visibility = View.GONE
            val adapter = TodaysOrderAdapter(this)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
            if(it.size == 0){
                no_orders_layout.visibility = View.VISIBLE
            }else{
                no_orders_layout.visibility = View.GONE
            }
            adapter.addItems(it)
        })
    }

    override fun todaysOrderReceivedFailed(message: String) {
        progressBar.visibility = View.GONE
        error_toast(message)
        Log.e(TAG, "todaysOrderReceivedFailed: $message" )
    }

    override fun onResume() {
        super.onResume()
        orderViewModel.todaysOrderListListener = this
        orderViewModel.getTodaysOrderList()
    }

    override fun onStarted() {
        loadingBar.showDialog()
    }

    override fun onSuccess(response: LiveData<ModelBasicResponse>) {
        response.observe(this, {
            if (it.status_code == 200) {
                loadingBar.cancelDialog()
                pref.clearPreference()
                Navigator.sharedInstance.navigateClear(this, LoginActivity::class.java)
            }
        })
    }

    override fun onFailure(message: String) {
        loadingBar.cancelDialog()
        error_toast(message)
    }

    fun firebaseToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            Log.d("Token", it.token)
            authViewModel.sendToken(it.token.toString()).observe(this, Observer {
                if (it.status_code == 200) {
                    Log.d(TAG, "firebaseToken: success")
                }
            })

        }

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_GLOBAL)
            .addOnCompleteListener { task ->

                if (task.isSuccessful)
                    Log.d(TAG, "Global topic subscription successful")
                else
                    Log.e(
                        TAG,
                        "Global topic subscription failed. Error: " + task.exception?.localizedMessage
                    )
            }
    }


}