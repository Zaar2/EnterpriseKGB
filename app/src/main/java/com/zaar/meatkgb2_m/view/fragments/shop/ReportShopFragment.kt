package com.zaar.meatkgb2_m.view.fragments.shop

import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaar.meatkgb2_m.R
import com.zaar.meatkgb2_m.data.RecordShopReportOut
import com.zaar.meatkgb2_m.utilities.types.TypeReports
import com.zaar.meatkgb2_m.utilities.view.ViewUtilities
import com.zaar.meatkgb2_m.view.activity.reports.SearchActivity
import com.zaar.meatkgb2_m.view.adapters.report.ShopReportRecViewAdapter
import com.zaar.meatkgb2_m.viewModel.viewModels.reports.ShopReportsVM

open class ReportShopFragment(
    private val shop:String,
    private val typeReports: TypeReports,
) : Fragment() {
    protected lateinit var model: ShopReportsVM
    protected var firstDate: String? = null
    protected var lastDate: String? = null

    protected fun observeLiveDataFillingRecView(
        recView: RecyclerView,
        tvCountItems: TextView,
        progressBarLayout: LinearLayout,
        reportShopLayout: LinearLayout,
    ) {
        model.ldReportShop().observe(
            this
        ) {
            if (it.isNotEmpty()) {
                tvCountItems.text = it.size.toString()
                when (typeReports) {
                    TypeReports.SHOP_DAY -> initRecView(itemsDaily = it, recView = recView)
                    TypeReports.SHOP_PERIOD -> initRecView(itemsPeriod = it, recView = recView)
                }
                if (typeReports == TypeReports.SHOP_DAY)
                    model.getListWorkers(typeReports)
            } else {
                tvCountItems.text = "0"
                when (typeReports) {
                    TypeReports.SHOP_DAY -> initRecView(recView = recView)
                    TypeReports.SHOP_PERIOD -> initRecView(recView = recView)
                }
                if (typeReports == TypeReports.SHOP_DAY)
                    model.getListWorkers(typeReports)
            }
            progressBarOff(progressBarLayout, reportShopLayout)
        }
    }

    protected fun observeLiveDataListWorkers(
        tvListWorkers: TextView,
    ) {
        model.ldNamesWorkers().observe(
            this
        ) {
            if (it.isNotEmpty()) {
                val str: String = it.joinToString(separator = "\n")
                tvListWorkers.text = str
            } else tvListWorkers.text = getString(R.string.tv_label_not_data)
        }
    }

    private fun initRecView(
        itemsDaily: List<RecordShopReportOut>? = null,
        itemsPeriod: List<RecordShopReportOut>? = null,
        recView: RecyclerView
    ) {
        var itemOnClickListener: ShopReportRecViewAdapter.ItemOnClickListener? = null

        recView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )

        if (itemsPeriod != null && itemsDaily == null)
            itemOnClickListener = object : ShopReportRecViewAdapter.ItemOnClickListener {
                override fun onItemClick(item: RecordShopReportOut) {
                    startActivity(Intent(context, SearchActivity::class.java).apply {
                        putExtra(getString(R.string._source), getString(R.string._periodReportShop))
                        putExtra(getString(R.string._nameProduct), item.productName)
                        putExtra(getString(R.string._firstDate), firstDate)
                        putExtra(getString(R.string._lastDate), lastDate)
                        putExtra(getString(R.string._countDays), item.countDays)
                    })
                }
            }

        recView.adapter = ShopReportRecViewAdapter(
            dailyRecords = itemsDaily, periodRecords = itemsPeriod,
            itemOnClickListener = itemsPeriod?.let { itemOnClickListener }
        )
    }

    protected fun tvDateProducedOnClick(
        textView: TextView
    ) {
        textView.setOnClickListener {
            context?.apply {
                ViewUtilities().callDatePicker(it as TextView, this)
            }
        }
    }

    private fun progressBarOff(
        progressBarLayout: LinearLayout,
        reportShopLayout: LinearLayout,
    ) {
        progressBarLayout.visibility = View.GONE
        reportShopLayout.visibility = View.VISIBLE
    }

    protected fun progressBarOn(
        progressBarLayout: LinearLayout,
        reportShopLayout: LinearLayout,
    ) {
        progressBarLayout.visibility = View.VISIBLE
        reportShopLayout.visibility = View.GONE
    }
}