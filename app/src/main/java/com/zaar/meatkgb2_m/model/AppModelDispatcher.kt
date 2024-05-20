package com.zaar.meatkgb2_m.model

import android.content.Context
import android.util.Log
import com.zaar.meatkgb2_m.data.RecordPreVSD
import com.zaar.meatkgb2_m.data.RecordSearchOut
import com.zaar.meatkgb2_m.data.RecordShopReportOut
import com.zaar.meatkgb2_m.model.repository.LocalDbRepositoryImpl
import com.zaar.meatkgb2_m.model.repository.RemoteRepositoryImpl
import com.zaar.meatkgb2_m.utilities.types.TypeReports
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import org.json.JSONArray
import org.json.JSONObject
import java.util.Collections.emptyList

class AppModelDispatcher(
    private val myContext: Context
) {
    fun getShopReport(
        idShop: Long,
        dateProduced: String,
        enterpriseId: String,
    ): Single<List<RecordShopReportOut>> {
        return RemoteRepositoryImpl()
            .getDailyShopReport("day", dateProduced, idShop, enterpriseId)
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val idArr = LocalDbRepositoryImpl(myContext).addDailyReportShop(it)
                if (idArr.isNotEmpty()) {
                    LocalDbRepositoryImpl(myContext).getAllRecordsOfReport(
                        TypeReports.SHOP_DAY
                    )
                } else {
                    Log.d("error caching", "daily report caching is failed!!!")
                    LocalDbRepositoryImpl(myContext).deleteReportShops(TypeReports.SHOP_DAY)
                    emptyList()
                }
            }
    }

    fun getShopReport(
        idShop: Long,
        firstDay: String,
        lastDay: String,
        enterpriseId: String,
    ): Single<List<RecordShopReportOut>> {
        return RemoteRepositoryImpl().getPeriodShopReport(
            "period",
            firstDay,
            lastDay,
            idShop,
            enterpriseId
        )
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val idArr: LongArray = LocalDbRepositoryImpl(myContext).addPeriodReportShop(it)
                if (idArr.isNotEmpty()) {
                    return@map LocalDbRepositoryImpl(myContext).getAllRecordsOfReport(
                        TypeReports.SHOP_PERIOD
                    )
                } else {
                    Log.d("error caching", "period report caching is failed!!!")
                    LocalDbRepositoryImpl(myContext).deleteReportShops(TypeReports.SHOP_PERIOD)
                    return@map emptyList<RecordShopReportOut>()
                }
            }
    }

    fun getListWorkersOfReports(type: TypeReports): List<String> {
        return LocalDbRepositoryImpl(myContext).getListWorkers(type)
    }

    fun getPreVsd(
        enterpriseId: String,
        firstDay: String,
        lastDay: String,
    ): Single<List<RecordPreVSD>> {
        return RemoteRepositoryImpl().getReportForPreVSD(
            enterpriseId, firstDay, lastDay
        )
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val idArr: LongArray = LocalDbRepositoryImpl(myContext).addPreVsd(it)
                if (idArr.isNotEmpty()) {
                    return@map LocalDbRepositoryImpl(myContext).getAllRecordsPreVsd()
                } else {
                    Log.d("error caching", "preVsd report caching is failed!!!")
                    LocalDbRepositoryImpl(myContext).deleteReportPreVsd()
                    return@map emptyList<RecordPreVSD>()
                }
            }
    }

    fun setVsdIsCreated(
        items: List<RecordPreVSD>,
        idEnterprise: String
    ): Single<Boolean> {
        val trueList: MutableList<String> = mutableListOf()
        val falseList: MutableList<String> = mutableListOf()
        items.forEach {
            if (it.vsd_isCreated) {
                trueList.add(it.id.toString())
            } else
                falseList.add(it.id.toString())
        }
        val body = JSONObject().let { mainObj ->
            mainObj.put("records_ids", JSONObject().let {
                it.put("true", JSONArray(trueList))
                it.put("false", JSONArray(falseList))
            })
            mainObj.put("enterpriseId", idEnterprise)
        }.toString()

        return RemoteRepositoryImpl().setVsdIsCreated(body)
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                if (it >= 0) {
                    /** for fully cached version
                    val listName = mutableListOf<String>()
                    listName.also {
                    items.forEach { record -> it.add(record.productName) }
                    }
                    val preVsdList = ConvertTypes().recordPreVsdToPreVsd(
                    items,
                    LocalDbRepositoryImpl(myContext).getMapNameIdByName(
                    listName
                    )
                    )
                    val idArr: LongArray = LocalDbRepositoryImpl(myContext).addPreVsd(preVsdList)
                    if (idArr.isNotEmpty()) {
                     */
                    return@map true
                    /** for fully cached version (continued)
                    } else {
                    Log.d(
                    "error set vsd is created",
                    "update report for preVsd with changed value of vsdIsCreated in the local DB - is failed!!!"
                    )
                    return@map false
                    }
                     */
                } else {
                    Log.d(
                        "error set vsd is created",
                        "change on remote server value of vsd is created - is failed!!!"
                    )
                    return@map false
                }
            }
    }

    fun getReportSearchByProduct(
        firstDate: String,
        lastDate: String,
        productName: String
    ): Single<List<RecordSearchOut>> {
        val productId: Long = LocalDbRepositoryImpl(myContext).getIdProductByName(productName)
        val cryptoId = LocalDbRepositoryImpl(myContext).getCryptoIdEnterprise()
        return RemoteRepositoryImpl().getReportSearchByProduct(
            firstDate, lastDate, productId, cryptoId
        )
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val idArr: LongArray = LocalDbRepositoryImpl(myContext).addReportSearch(it)
                if (idArr.isNotEmpty()){
                    return@map LocalDbRepositoryImpl(myContext).getAllRecordsSearch()
                }else{
                    Log.d("error caching", "RecordSearch report caching is failed!!!")
                    LocalDbRepositoryImpl(myContext).deleteReportSearch()
                    return@map emptyList<RecordSearchOut>()
                }
            }
    }

    /*  for testing method 'getReportSearchByProduct()'
    fun getStringSearchByProduct(
        firstDate: String,
        lastDate: String,
        productName: String
    ): String {
        val productId: Long = LocalDbRepositoryImpl(myContext).getIdProductByName(productName)
        val cryptoId = LocalDbRepositoryImpl(myContext).cryptoIdEnterprise
        var str:String
        RemoteRepositoryImpl().getStringSearchByProduct(
            firstDate, lastDate, productId, cryptoId
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                return@map it
            }
            .subscribe(object : DisposableSingleObserver<String>() {
                override fun onSuccess(t: String) {
                    str= t
                }

                override fun onError(e: Throwable) {
                    str=""
                }
            })
        return ""
    }
     */
}