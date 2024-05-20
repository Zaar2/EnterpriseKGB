package com.zaar.meatkgb2_m.view.activity.reports

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.zaar.meatkgb2_m.R
import com.zaar.meatkgb2_m.data.RecordSearchOut
import com.zaar.meatkgb2_m.databinding.ActivitySearchBinding
import com.zaar.meatkgb2_m.utilities.view.ViewUtilities
import com.zaar.meatkgb2_m.view.adapters.report.SearchAdapter
import com.zaar.meatkgb2_m.viewModel.factory.reports.SearchFactory
import com.zaar.meatkgb2_m.viewModel.viewModels.reports.SearchVM

class SearchActivity : AppCompatActivity() {
    private var _binding: ActivitySearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var model: SearchVM
    private lateinit var spinnerItems: Array<String>
    private var fromReportShop: Boolean = false
    private var firstDateFromReportShop: String = ""
    private var lastDateFromReportShop: String = ""
    private var nameProductFromReportShop: String = ""
    private var countDaysProduced: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVariables()
        initObserve()
        initView()
    }


    private fun initVariables() {
        model = ViewModelProvider(
            this,
            SearchFactory(
                applicationContext,
                "SearchActivity",
            )
        ).get()
        spinnerItems = arrayOf("non")
        val source: String = intent.getStringExtra(getString(R.string._source)).toString()
        if (source == getString(R.string._periodReportShop)) {
            fromReportShop = true
            firstDateFromReportShop =
                intent.getStringExtra(getString(R.string._firstDate)).toString()
            lastDateFromReportShop =
                intent.getStringExtra(getString(R.string._lastDate)).toString()
            nameProductFromReportShop =
                intent.getStringExtra(getString(R.string._nameProduct)).toString()
            countDaysProduced =
                intent.getStringExtra(getString(R.string._countDays)).toString()
        }
    }

    private fun initObserve() {
        initObserveViewModel()
        initObserveView()
    }

    private fun initObserveViewModel() {
        model.ldNameProducts().observe(this) {
            if (it.isNotEmpty()) spinnerItems = it.toTypedArray()
            else spinnerItems = arrayOf("non")
            binding.spinnerProduct.adapter = ArrayAdapter(
                this,
                R.layout.item_spinner,
                spinnerItems
            )
            if (fromReportShop)
                binding.spinnerProduct.setSelection(
                    (binding.spinnerProduct.adapter as ArrayAdapter<String>)
                        .getPosition(nameProductFromReportShop)
                )
            binding.btnGetReportForSearchSearch.performClick()
        }

        model.ldRecordsOfProduced().observe(this) {
            if (it != null) {
                showCountRows(it.size)
                showSumProduct(getSumProducts(it))
                recView(it)
            }
            progressBarOff()
        }
    }

    private fun recView(items: List<RecordSearchOut>) {
        binding.layoutSearch.recViewSearch.adapter =
            SearchAdapter(
                items
            )
    }

    private fun initObserveView() {
        tvDateProducedOnClick(binding.firstDateValueSearch)
        tvDateProducedOnClick(binding.lastDateValueSearch)
        btnGetReportOnClick()
    }

    private fun initView() {
        //set current date
        if (!fromReportShop) {
            ViewUtilities().initDateDefaultForTextView(binding.firstDateValueSearch, 0)
            ViewUtilities().initDateDefaultForTextView(binding.lastDateValueSearch, 0)
        } else {
            binding.firstDateValueSearch.text = firstDateFromReportShop
            binding.lastDateValueSearch.text = lastDateFromReportShop
        }
        //setting textViews
        showCountRows(0)
        showSumProduct(0f)
        if (fromReportShop) {
            tvCountDaysOn()
            showDaysProduced(countDaysProduced)
        }
        binding.firstDateValueSearch.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.lastDateValueSearch.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        //setting recycle view
        val rec: RecyclerView = binding.layoutSearch.recViewSearch
        val layoutManager: LayoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        rec.layoutManager = layoutManager
        rec.addItemDecoration(
            DividerItemDecoration(
                rec.context,
                LinearLayoutManager.VERTICAL
            )
        )
        //setting spinner
        binding.spinnerProduct.adapter = ArrayAdapter(
            this,
            R.layout.item_spinner,
            spinnerItems
        )
        model.getAllNamesProduct()
    }


    private fun tvDateProducedOnClick(
        textView: TextView
    ) {
        textView.setOnClickListener {
            ViewUtilities().callDatePicker(it as TextView, this)
        }
    }

    private fun btnGetReportOnClick() {
        binding.btnGetReportForSearchSearch.setOnClickListener {
            progressBarOn()
            if (!fromReportShop) {
                tvCountDaysOff()
            }
            val spin:String=binding.spinnerProduct.selectedItem.toString()
            fromReportShop = false
            model.getReportSearchByProduct(
                binding.firstDateValueSearch.text.toString(),
                binding.lastDateValueSearch.text.toString(),
                spin
//                binding.spinnerProduct.selectedItem.toString()
            )
        }
    }

    private fun getSumProducts(items: List<RecordSearchOut>): Float {
        var sum = 0f
        items.forEach {
            sum += it.count
//            sum = sum.plus(it.count)
        }
        return sum
    }

    private fun showCountRows(number: Int) {
        binding.tvNumRecordsSearch.text = number.toString()
    }

    private fun showSumProduct(number: Float) {
        binding.tvSumProductCountValue.text = number.toString()
    }

    private fun showDaysProduced(count: String) {
        binding.tvCountDaysProducedValue.text = count
    }

    private fun progressBarOn() {
        binding.layoutSearch.root.visibility = View.GONE
        binding.layoutProgressBarSearch.root.visibility = View.VISIBLE
    }

    private fun progressBarOff() {
        binding.layoutSearch.root.visibility = View.VISIBLE
        binding.layoutProgressBarSearch.root.visibility = View.GONE
    }

    private fun tvCountDaysOn(){
        binding.tvCountDaysProducedLabel.visibility = View.VISIBLE
        binding.tvCountDaysProducedValue.visibility = View.VISIBLE
    }

    private fun tvCountDaysOff(){
        binding.tvCountDaysProducedLabel.visibility = View.GONE
        binding.tvCountDaysProducedValue.visibility = View.GONE
    }
}