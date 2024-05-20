package com.zaar.meatkgb2_m.viewModel.viewModels.reports

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.zaar.meatkgb2_m.data.RecordShopReportOut
import com.zaar.meatkgb2_m.model.AppModelDispatcher
import com.zaar.meatkgb2_m.model.repository.LocalDbRepositoryImpl
import com.zaar.meatkgb2_m.utilities.types.TypeReports
import com.zaar.meatkgb2_m.utilities.viewModel.VMUtilities
import com.zaar.meatkgb2_m.viewModel.viewModels.BaseVM
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.Locale

open class ShopReportsVM(
    myContext: Context,
    initiator: TypeReports,
    state: SavedStateHandle
): BaseVM(myContext, initiator.name) {

    private var mldReportShop = MutableLiveData<List<RecordShopReportOut>>()
    fun ldReportShop(): LiveData<List<RecordShopReportOut>> = mldReportShop

    private var mldNamesWorkers = MutableLiveData<List<String>>()
    fun ldNamesWorkers(): LiveData<List<String>> = mldNamesWorkers

    private var mldCountDaysPeriodReport = MutableLiveData<Int>()
    fun ldCountDaysPeriodReport(): LiveData<Int> = mldCountDaysPeriodReport

    private val savedStateHandle = state


    fun getShopReport(
        shop: String = "", dateProduced: String = "",
        firstDay: String = "", lastDay: String = "",
        typeReports: TypeReports
    ) {
        var response: Single<List<RecordShopReportOut>>?
        val enterpriseId = LocalDbRepositoryImpl(myContext).getCryptoIdEnterprise()
        val idShop: Long = LocalDbRepositoryImpl(myContext).getIdShopByName(shop)
        response = if (enterpriseId != "") {
            when (typeReports) {
                TypeReports.SHOP_DAY -> AppModelDispatcher(myContext)
                    .getShopReport(
                        idShop, dateProduced, enterpriseId
                    )

                TypeReports.SHOP_PERIOD -> myContext.let {
                    AppModelDispatcher(it).getShopReport(
                        idShop, firstDay, lastDay, enterpriseId
                    )
                }
            }
        } else null
        response
            ?.subscribeOn(Schedulers.io())
            ?.subscribe(object : DisposableSingleObserver<List<RecordShopReportOut>>() {
                override fun onSuccess(t: List<RecordShopReportOut>) {
                    val days: Long? = when (typeReports) {
                        TypeReports.SHOP_PERIOD -> VMUtilities().diffBetweenDates(
                            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()),
                            firstDay, lastDay
                        )

                        else -> null
                    }
                    if (days != null) mldCountDaysPeriodReport.value = days.plus(1).toInt()
                    mldReportShop.value = t
                }

                override fun onError(e: Throwable) {
                    Log.d("Error in MainShopReportsViewModel.getShopReportDay()", e.toString())
                    Log.d("HTTP request code is", getDescriptionLineFromThrowable(e))
                }
            })
    }

    fun getListWorkers(type: TypeReports) {
        mldNamesWorkers.value = myContext.let { AppModelDispatcher(it).getListWorkersOfReports(type) }
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun saveCurrentUser(userId: String) {
        // Sets a new value for the object associated to the key.
        savedStateHandle["userID"] = userId
    }

    fun getCurrentUser(): String {
        // Gets the current value of the user id from the saved state handle
        return savedStateHandle["userID"] ?: ""
    }
}