package com.zaar.meatkgb2_m.model.local.api_room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.zaar.meatkgb2_m.data.RecordPreVSD
import com.zaar.meatkgb2_m.model.entity.PreVsd
import com.zaar.meatkgb2_m.model.entity.RecordDailyReport
import com.zaar.meatkgb2_m.model.entity.RecordPeriodReport

@Dao
interface PreVsdDao: BaseDao<PreVsd> {

    @Query("delete from preVsd")
    fun deleteAll(): Int

    @Transaction
    fun insertWithReplace(report: List<PreVsd>): LongArray {
        deleteAll()
        return insert(report)
    }

    @Query(
        "select " +
                " vsd.id as id," +
                " vsd.date_produced as date_produced," +
                " Products.product_name as productName," +
                " vsd.count as count," +
                " vsd.time_produced as time_produced," +
                " vsd.vsd_isCreated as vsd_isCreated," +
                " vsd.vsd_support as vsd_support," +
                " vsd.enterpriseId as enterpriseId" +
                " from preVsd as vsd, Products" +
                " where vsd.id_product = Products.id" +
                " order by date_produced desc, Products.product_name asc, time_produced desc"
    )
    fun getReportPreVsd(): List<RecordPreVSD>


}