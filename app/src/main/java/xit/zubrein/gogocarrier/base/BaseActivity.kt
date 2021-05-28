package xit.zubrein.gogocarrier.base

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import xit.zubrein.gogocarrier.Utils.LoadingBar
import xit.zubrein.gogocarrier.Utils.pref.PreferenceManager
import xit.zubrein.gogocarrier.ui.auth.AuthViewModel
import xit.zubrein.gogocarrier.ui.dashboard.DashboardViewModel
import xit.zubrein.gogocarrier.ui.order.OrderViewModel
import xit.zubrein.gogocarrier.ui.profile.ProfileViewModel
import javax.inject.Inject


abstract class BaseActivity <T : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: T

    @Inject
    lateinit var loadingBar: LoadingBar

    @Inject
    lateinit var pref : PreferenceManager

    //Viewmodels
    val authViewModel : AuthViewModel by viewModels()
    val orderViewModel : OrderViewModel by viewModels()
    val dashboardViewModel : DashboardViewModel by viewModels()
    val profileViewModel : ProfileViewModel by viewModels()

    protected val TAG: String  by lazy {
        this.javaClass.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,getView())
        onViewReady(savedInstanceState, intent)

    }

    abstract fun getView() : Int
    abstract fun onViewReady(savedInstanceState: Bundle?, intent: Intent)


}