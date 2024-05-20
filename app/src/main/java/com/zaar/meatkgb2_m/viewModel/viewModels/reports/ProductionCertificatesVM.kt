package com.zaar.meatkgb2_m.viewModel.viewModels.reports

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zaar.meatkgb2_m.data.RecordPreVSD
import com.zaar.meatkgb2_m.model.repository.LocalDbRepositoryImpl
import com.zaar.meatkgb2_m.viewModel.viewModels.BaseVM
import com.zaar.meatkgb2_m.model.AppModelDispatcher
import com.zaar.meatkgb2_m.utilities.types.TypeReports
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ProductionCertificatesVM(
    myContext: Context,
    initiator: String,
): BaseVM(myContext, initiator) {
    private var mldReportPreVsd = MutableLiveData<List<RecordPreVSD>>()
    fun ldReportPreVsd(): LiveData<List<RecordPreVSD>> = mldReportPreVsd

    private var mldResultSetVsdIsCreated = MutableLiveData<Boolean>()
    fun ldResultSetVsdIsCreated(): LiveData<Boolean> = mldResultSetVsdIsCreated

    fun getProductsForCertification(
        firstDay: String,
        lastDay: String,
    ) {
        val enterpriseId = LocalDbRepositoryImpl(myContext).getCryptoIdEnterprise()
        val response = if (enterpriseId != "") {
            AppModelDispatcher(myContext).getPreVsd(enterpriseId, firstDay, lastDay)
        } else null
        response
            ?.subscribeOn(Schedulers.io())
            ?.subscribe(object : DisposableSingleObserver<List<RecordPreVSD>>() {
                override fun onSuccess(records: List<RecordPreVSD>) {
                    mldReportPreVsd.value = records
                }

                override fun onError(e: Throwable) {
                    Log.d(
                        "Error in ProductionCertificatesViewModel.getProductForCertification()",
                        e.toString()
                    )
                    Log.d(
                        "HTTP request code is",
                        getDescriptionLineFromThrowable(e)
                    )
                }
            })
    }

    fun saveToServer(
        items: List<RecordPreVSD>
    ) {
        val idEnterprise = LocalDbRepositoryImpl(myContext).getCryptoIdEnterprise()
//        val enterpriseId = LocalDbRepositoryImpl(myContext).cryptoIdEnterprise ?: ""
        val response = if (idEnterprise != "") {
            AppModelDispatcher(myContext).setVsdIsCreated(items, idEnterprise)
        } else null
        val result = response
            ?.subscribeOn(Schedulers.io())
            ?.subscribe(object : DisposableSingleObserver<Boolean>() {
                override fun onSuccess(t: Boolean) {
                    mldResultSetVsdIsCreated.value = t
                }

                override fun onError(e: Throwable) {
                    Log.d(
                        "Error in ProductionCertificatesViewModel.saveToServer()",
                        e.toString()
                    )
                    Log.d(
                        "HTTP request code is",
                        getDescriptionLineFromThrowable(e)
                    )
                    mldResultSetVsdIsCreated.value = false
                }
            })
        if (result == null)
            mldResultSetVsdIsCreated.value = false
    }
}