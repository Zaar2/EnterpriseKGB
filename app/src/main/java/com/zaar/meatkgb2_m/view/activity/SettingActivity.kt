package com.zaar.meatkgb2_m.view.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.zaar.meatkgb2_m.R
import com.zaar.meatkgb2_m.databinding.ActivitySettingBinding
import com.zaar.meatkgb2_m.viewModel.factory.settings.SettingFactory_base
import com.zaar.meatkgb2_m.viewModel.viewModels.setting.BaseVM_setting
open class SettingActivity : AppCompatActivity() {
    private var binding: ActivitySettingBinding? = null
     var navController: NavController? = null
    private var modelSettingBase: BaseVM_setting? = null
    private val message = "Setting"
    private var type: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initObserve()
        initVariable()
    }

    private fun initVariable() {
        navController = findNavController(this, R.id.setting_nav_host_graph)
        type = intent.extras?.getString("type")
        modelSettingBase = ViewModelProvider(
            this,
            SettingFactory_base(this.applicationContext, message)
        )[BaseVM_setting::class.java]
    }

    private fun initObserve() {
//        modelSettingBase
    }

    override fun onDestroy() {
        super.onDestroy()
        modelSettingBase?.clearContext()
    }
}