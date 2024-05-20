package com.zaar.meatkgb2_m.viewModel.factory.reports

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zaar.meatkgb2_m.utilities.types.TypeReports
import com.zaar.meatkgb2_m.viewModel.viewModels.reports.ShopReportsVM

class ShopReportFactory(
    private val myContext: Context,
    private val initiator: TypeReports,
    private val state: SavedStateHandle,
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShopReportsVM(myContext, initiator, state) as T
    }
}