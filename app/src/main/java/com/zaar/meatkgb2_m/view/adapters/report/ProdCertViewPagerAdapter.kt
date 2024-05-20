package com.zaar.meatkgb2_m.view.adapters.report

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zaar.meatkgb2_m.utilities.types.TypeReports
import com.zaar.meatkgb2_m.view.fragments.ProdCertFragment
import com.zaar.meatkgb2_m.view.fragments.shop.DayReportShopFragment
import com.zaar.meatkgb2_m.view.fragments.shop.PeriodReportShopFragment

class ProdCertViewPagerAdapter(fragmentActivity: FragmentActivity):
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) ProdCertFragment(TypeReports.SHOP_DAY)
        else ProdCertFragment(TypeReports.SHOP_PERIOD)
    }
}