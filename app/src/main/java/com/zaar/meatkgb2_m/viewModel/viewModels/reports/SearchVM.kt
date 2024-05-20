package com.zaar.meatkgb2_m.viewModel.viewModels.reports

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zaar.meatkgb2_m.data.RecordSearchOut
import com.zaar.meatkgb2_m.data.RecordsOfProduced
import com.zaar.meatkgb2_m.model.AppModelDispatcher
import com.zaar.meatkgb2_m.model.entity.RecordSearch
import com.zaar.meatkgb2_m.viewModel.viewModels.BaseVM
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class SearchVM(
    myContext: Context,
    initiator: String,
): BaseVM(myContext, initiator) {

    private var mldRecordsOfProduced = MutableLiveData<List<RecordSearchOut>?>()
    fun ldRecordsOfProduced(): LiveData<List<RecordSearchOut>?> = mldRecordsOfProduced
    fun getReportSearchByProduct(
        firstDate: String,
        lastDate: String,
        productName: String
    ) {
//        AppModelDispatcher(myContext).getStringSearchByProduct(firstDate, lastDate, productName)
        AppModelDispatcher(myContext).getReportSearchByProduct(
            firstDate, lastDate, productName
        )
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableSingleObserver<List<RecordSearchOut>>() {
                override fun onSuccess(t: List<RecordSearchOut>) {
                    mldRecordsOfProduced.value = t
                }

                override fun onError(e: Throwable) {
                    mldRecordsOfProduced.value = null
                }
            })
    }
}