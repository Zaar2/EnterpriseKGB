package com.zaar.meatkgb2_m.model.local.api_room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.zaar.meatkgb2_m.data.RecordShopReportOut
import com.zaar.meatkgb2_m.model.entity.RecordPeriodReport

@Dao
interface ShopPeriodReportDao: BaseDao<RecordPeriodReport> {

//    @Query("select distinct Workers.full_name" +
//            " from shopPeriodReport, Workers " +
//            " where shopPeriodReport.id_worker == Workers.id")
//    fun getListWorkersOfReports(): List<String>

    @Query("delete from shopPeriodReport")
    fun deleteAll(): Int

    @Transaction
    fun insertWithReplace(report: List<RecordPeriodReport>): LongArray {
        deleteAll()
        return insert(report)
    }

    @Query(
        "select" +
                " report.id," +
                " products.product_name as product_name," +
                " report.count," +
                " report.countOfBathes," +
                " report.countDays," +
                " 'non' as date_produced," +
                " 'non' as full_name," +
                " 'non' as time_produced," +
                " Products.me as me" +
                " from shopPeriodReport as report, Products" +
                " where products.id == report.id_product" +
                " order by product_name asc"
    )
    fun getReportsOfPeriod(): List<RecordShopReportOut>

    //all records from last report for period
//    "select report.id, report.date_produced, hum.full_name, prod.product_name, report.count, report.time_produced" +
//    " from shopPeriodReport as report, Products as prod, Workers as hum" +
//    " where prod.id == report.id_product and hum.id == report.id_worker"
}