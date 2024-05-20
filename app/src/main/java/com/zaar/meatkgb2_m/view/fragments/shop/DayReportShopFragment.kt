package com.zaar.meatkgb2_m.view.fragments.shop

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.zaar.meatkgb2_m.databinding.FragmentReportShopDayBinding
import com.zaar.meatkgb2_m.utilities.types.TypeReports
import com.zaar.meatkgb2_m.utilities.view.ViewUtilities
import com.zaar.meatkgb2_m.viewModel.factory.reports.ShopReportFactory

class DayReportShopFragment(
    private val shop: String,
) : ReportShopFragment(shop, TypeReports.SHOP_DAY) {

    private var _binding: FragmentReportShopDayBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportShopDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnGetReportForDay.performClick()
    }

//    override fun onResume() {
//        super.onResume()
//        binding.btnGetReportForDay.performClick()
//    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        initVariables()
        initObserve()
        initView()

        binding.btnGetReportForDay.performClick()
    }

    private fun initVariables() {
        model = ViewModelProvider(
            this,
            ShopReportFactory(
                requireActivity().applicationContext,
                TypeReports.SHOP_DAY,
                SavedStateHandle()
            )
        ).get()
    }

    private fun initView() {
        binding.layoutReportShop.stubReportForDay.visibility = View.VISIBLE
        ViewUtilities().initDateDefaultForTextView(binding.tvFirstDateValue, 0)
        binding.tvFirstDateValue.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun initObserve() {
        initObserveLiveData()
        initObserveView()
    }

    private fun initObserveLiveData() {
        observeLiveDataFillingRecView(
            binding.layoutReportShop.recViewReportShop,
            binding.tvValue1NumRecords,
            binding.progressBarRepShop.root,
            binding.layoutReportShop.root
        )
        observeLiveDataListWorkers(
            binding.tvWorkersForReport,
        )
    }

    private fun initObserveView() {
        btnGetReportOnClick()
        tvDateProducedOnClick(binding.tvFirstDateValue)
    }

    private fun btnGetReportOnClick() {
        binding.btnGetReportForDay.setOnClickListener {
            progressBarOn(
                binding.progressBarRepShop.root,
                binding.layoutReportShop.root
            )
            model.getShopReport(
                shop = shop,
                dateProduced = binding.tvFirstDateValue.text.toString(),
                typeReports = TypeReports.SHOP_DAY
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}