package com.zaar.meatkgb2_m.view.activity.reports

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zaar.meatkgb2_m.R
import com.zaar.meatkgb2_m.databinding.ActivityShopReportsBinding
import com.zaar.meatkgb2_m.view.adapters.report.ShopReportsViewPagerAdapter

open class ShopReportsActivity : FragmentActivity() {

    private lateinit var binding: ActivityShopReportsBinding

    private lateinit var adapter: ShopReportsViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val tabNames: Array<String> by lazy { resources.getStringArray(R.array.arrShopReports) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val shopName: String = (intent.getStringExtra("shop")) ?: "non"
        binding.shopReportTitle.text = shopName
        adapter = ShopReportsViewPagerAdapter(this, shopName)
        viewPager = binding.viewPagerShopReports
        viewPager.adapter = adapter

        tabLayout = binding.tabLayoutShopReports
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }
}