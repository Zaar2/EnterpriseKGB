package com.zaar.meatkgb2_m.view.fragments

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaar.meatkgb2_m.R
import com.zaar.meatkgb2_m.data.RecordPreVSD
import com.zaar.meatkgb2_m.databinding.FragmSupportVsdBinding
import com.zaar.meatkgb2_m.utilities.types.TypeReports
import com.zaar.meatkgb2_m.utilities.view.ViewUtilities
import com.zaar.meatkgb2_m.view.adapters.report.ProdCertAdapter
import com.zaar.meatkgb2_m.viewModel.factory.reports.ProductionCertificatesFactory
import com.zaar.meatkgb2_m.viewModel.viewModels.reports.ProductionCertificatesVM

class ProdCertFragment(
    private val type: TypeReports
): Fragment() {
    private var _binding: FragmSupportVsdBinding? = null
    private val binding get() = _binding!!

    private var model: ProductionCertificatesVM? = null
    private lateinit var itemOnClickListener: ProdCertAdapter.ItemOnClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmSupportVsdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnGetReportForSearch.performClick()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        initVariables()
        if (model!=null) {
            initObserve()
            initView()
            if (type == TypeReports.SHOP_PERIOD)
                inAdditionInit()
        }
    }


    private fun initVariables() {
        this.context?.let {
            model = ViewModelProvider(
                this,
                ProductionCertificatesFactory(
                    it.applicationContext,
                    "ProductionCertificatesActivity",
                )
            ).get()
        }
    }

    private fun initView() {
        ViewUtilities().activeBtnOff(
            binding.btnSaveToServer,
            requireContext().getColorStateList(R.color.button_blocked),
            requireContext().getColorStateList(R.color.black)
        )
        ViewUtilities().initDateDefaultForTextView(binding.tvFirstDateValue, 0)
        binding.layoutReportShop.labelForListViewRecordsForShop.checkboxProdCertIsCreated
            .visibility = View.GONE
        progressBarOff()
        val recView: RecyclerView = binding.layoutReportShop.RecViewRecordsProdCert
        recView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        recView.addItemDecoration(
            DividerItemDecoration(
                recView.context,
                LinearLayoutManager.VERTICAL
            )
        )
//        binding.btnGetReportForSearch.performClick()
        binding.linLayoutLastDate.visibility=View.GONE
        binding.tvFirstDateValue.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    /**
     * if type of fragment is 'period'
     */
    private fun inAdditionInit() {
        binding.linLayoutLastDate.visibility = View.VISIBLE
        binding.btnGetReportForSearch.text = getString(R.string.btn_label_getReport_forPeriod)
        ViewUtilities().initDateDefaultForTextView(binding.tvLastDateValue, 0)
        tvDateProducedOnClick(binding.tvLastDateValue)
        binding.tvLastDateValue.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun initObserve() {
        initObserveView()
        initObserveLiveData()
    }

    private fun initObserveView() {
        tvDateProducedOnClick(binding.tvFirstDateValue)
        btnGetReportOnClick()
        btnSaveToServer()
        itemOnClickListener = object : ProdCertAdapter.ItemOnClickListener {
            override fun onItemClick(checkBox: CheckBox?, item: RecordPreVSD) {
                ViewUtilities().activeBtnOn(
                    binding.btnSaveToServer,
                    requireContext().getColorStateList(R.color.bg_selector),
                    requireContext().getColorStateList(R.color.milk_background)
                )
                checkBox?.let {
                    it.isChecked = !it.isChecked
                    var num = binding.value2NumIsChecked.text.toString().toInt()
                    num +=
                        when (it.isChecked) {
                            true -> 1
                            false -> -1
                        }
                    item.vsd_isCreated = it.isChecked
                    showNumOfChecked(num)
                }
            }
        }
    }

    private fun initObserveLiveData() {
        model?.ldReportPreVsd()?.observe(viewLifecycleOwner) {
            binding.tvValue1NumRecords.text = it.size.toString()
            recView(it)
            showNumOfChecked(getNumVsdIsCreated(it))
            progressBarOff()
            ViewUtilities().activeBtnOff(
                binding.btnSaveToServer,
                requireContext().getColorStateList(R.color.button_blocked),
                requireContext().getColorStateList(R.color.black)
            )
        }
        model?.ldResultSetVsdIsCreated()?.observe(viewLifecycleOwner) {
            if (it) ViewUtilities().activeBtnOff(
                binding.btnSaveToServer,
                requireContext().getColorStateList(R.color.button_blocked),
                requireContext().getColorStateList(R.color.black)
            )
            else binding.btnGetReportForSearch.performClick()
            progressBarOff()
        }
    }

    private fun recView(items: List<RecordPreVSD>) {
        binding.layoutReportShop.RecViewRecordsProdCert.adapter = ProdCertAdapter(
            items,
            itemOnClickListener
        )
    }

    private fun tvDateProducedOnClick(
        textView: TextView
    ) {
        textView.setOnClickListener {
            context?.let { context -> ViewUtilities().callDatePicker(it as TextView, context) }
        }
    }

    private fun btnSaveToServer() {
        binding.btnSaveToServer.setOnClickListener {
            progressBarOn()
            model?.saveToServer(
                (binding.layoutReportShop.RecViewRecordsProdCert.adapter as ProdCertAdapter).getItems()
            )
        }
    }

    private fun btnGetReportOnClick() {
        binding.btnGetReportForSearch.setOnClickListener {
            progressBarOn()
            val firstDay = binding.tvFirstDateValue.text.toString()
            val lastDay =
                when (type) {
                    TypeReports.SHOP_DAY -> firstDay
                    TypeReports.SHOP_PERIOD -> binding.tvLastDateValue.text.toString()
                }
            model?.getProductsForCertification(
                firstDay, lastDay,
            )
        }
    }

    private fun getNumVsdIsCreated(itemsList: List<RecordPreVSD>): Int {
        var num = 0
        itemsList.forEach {
            num +=
                when (it.vsd_isCreated) {
                    true -> 1
                    false -> 0
                }
        }
        return num
    }

    private fun showNumOfChecked(number: Int) {
        binding.value2NumIsChecked.text = number.toString()
    }

    private fun progressBarOff() {
        binding.progressBarPreVSD.root.visibility = View.GONE
        binding.layoutReportShop.root.visibility = View.VISIBLE
    }

    private fun progressBarOn(){
        binding.layoutReportShop.root.visibility = View.GONE
        binding.progressBarPreVSD.root.visibility = View.VISIBLE
    }
}