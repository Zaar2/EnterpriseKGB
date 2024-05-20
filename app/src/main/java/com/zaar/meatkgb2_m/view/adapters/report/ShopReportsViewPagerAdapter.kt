package com.zaar.meatkgb2_m.view.adapters.report

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zaar.meatkgb2_m.view.fragments.shop.DayReportShopFragment
import com.zaar.meatkgb2_m.view.fragments.shop.PeriodReportShopFragment

class ShopReportsViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    shop:String
):FragmentStateAdapter(fragmentActivity) {

    private val shopName: String = shop
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return if (position == 0) DayReportShopFragment(shopName,)
        else PeriodReportShopFragment(shopName)
    }
}