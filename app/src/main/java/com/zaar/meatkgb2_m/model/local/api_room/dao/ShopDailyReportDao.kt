package com.zaar.meatkgb2_m.model.local.api_room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.zaar.meatkgb2_m.data.RecordShopReportOut
import com.zaar.meatkgb2_m.model.entity.RecordDailyReport

@Dao
interface ShopDailyReportDao: BaseDao<RecordDailyReport> {
    @Query("delete from shopDailyReport")
    fun deleteAll(): Int

    @Transaction
    fun insertWithReplace(report: List<RecordDailyReport>): LongArray {
        deleteAll()
        return insert(report)
    }

    @Query(
        "select" +
                " rep.id," +
                " rep.date_produced," +
                " hum.full_name," +
                " prod.product_name," +
                " rep.count," +
                " rep.time_produced," +
                " 0 as countOfBathes," +
                " 0 as countDays," +
                " prod.me as me" +
                " from shopDailyReport as rep, Products as prod, Workers as hum" +
                " where prod.id == rep.id_product and hum.id == rep.id_worker" +
                " order by rep.time_produced"
    )
    fun getDailyReports(): List<RecordShopReportOut>

    @Query(
        "select distinct Workers.full_name" +
                " from shopDailyReport, Workers " +
                " where shopDailyReport.id_worker == Workers.id"
    )
    fun getListWorkersOfReports(): List<String>
}