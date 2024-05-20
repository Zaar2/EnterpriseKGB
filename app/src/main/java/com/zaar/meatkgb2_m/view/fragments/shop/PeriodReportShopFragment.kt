package com.zaar.meatkgb2_m.view.fragments.shop

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.zaar.meatkgb2_m.databinding.FragmentReportShopPeriodBinding
import com.zaar.meatkgb2_m.utilities.types.TypeReports
import com.zaar.meatkgb2_m.utilities.view.ViewUtilities
import com.zaar.meatkgb2_m.viewModel.factory.reports.ShopReportFactory

class PeriodReportShopFragment(private val shop: String)
    : ReportShopFragment(shop, TypeReports.SHOP_PERIOD) {
    private var _binding: FragmentReportShopPeriodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportShopPeriodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        initVariables()
        initObserve()
        initView()
    }

    override fun onStart() {
        super.onStart()
//        binding.tvFirstDateValue.text = model.firstDate
//        binding.tvLastDateValue.text = model.lastDate
//        binding.btnGetReportForPeriod.performClick()
    }

    private fun initVariables() {
        model = ViewModelProvider(
            this,
            ShopReportFactory(
                requireActivity().applicationContext,
                TypeReports.SHOP_PERIOD,
                SavedStateHandle(),
            )
        ).get()
    }

    private fun initView() {
        binding.layoutReportShop.stubReportForPeriod.visibility = View.VISIBLE
        ViewUtilities().initDateDefaultForTextView(binding.tvFirstDateValue, 0)
        ViewUtilities().initDateDefaultForTextView(binding.tvLastDateValue, 0)
        binding.tvFirstDateValue.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.tvLastDateValue.paintFlags = Paint.UNDERLINE_TEXT_FLAG
//        binding.btnGetReportForPeriod.performClick()
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
        model.ldCountDaysPeriodReport().observe(viewLifecycleOwner) {
            binding.tvValueNumDays.text = it.toString()
        }
    }

    private fun initObserveView() {
        btnGetReportOnClick()
        tvDateProducedOnClick(binding.tvFirstDateValue)
        tvDateProducedOnClick(binding.tvLastDateValue)
    }

    private fun btnGetReportOnClick() {
        binding.btnGetReportForPeriod.setOnClickListener {
            progressBarOn(
                binding.progressBarRepShop.root,
                binding.layoutReportShop.root
            )
            firstDate = binding.tvFirstDateValue.text.toString()
            lastDate = binding.tvLastDateValue.text.toString()
            model.getShopReport(
                shop = shop,
                typeReports = TypeReports.SHOP_PERIOD,
                firstDay = firstDate ?: "",
                lastDay = lastDate ?: ""
//                firstDay = binding.tvFirstDateValue.text.toString(),
//                lastDay = binding.tvLastDateValue.text.toString(),
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}