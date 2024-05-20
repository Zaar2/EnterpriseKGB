package com.zaar.meatkgb2_m.viewModel.factory.reports

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zaar.meatkgb2_m.viewModel.viewModels.reports.ProductionCertificatesVM

class ProductionCertificatesFactory(
    private val myContext: Context,
    private val initiator: String,
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductionCertificatesVM(myContext, initiator,) as T
    }
}