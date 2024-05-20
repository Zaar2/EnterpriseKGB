package com.zaar.meatkgb2_m.model.local.api_room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.zaar.meatkgb2_m.data.RecordSearchOut
import com.zaar.meatkgb2_m.data.RecordShopReportOut
import com.zaar.meatkgb2_m.model.entity.RecordDailyReport
import com.zaar.meatkgb2_m.model.entity.RecordSearch

@Dao
interface ReportSearch: BaseDao<RecordSearch> {
    @Query("delete from searchReport")
    fun deleteAll(): Int

    @Transaction
    fun insertWithReplace(report: List<RecordSearch>): LongArray {
        deleteAll()
        return insert(report)
    }

    @Query(
        "select" +
                " rep.id as id,"+
                " rep.date_produced as date_produced," +
                " prod.product_name as product_name," +
                " rep.count as count," +
                " rep.time_produced as time_produced," +
                " rep.time_created as time_created,"+
                " hum.full_name as full_name" +
                " from searchReport as rep, Products as prod, Workers as hum" +
                " where prod.id == rep.id_product and hum.id == rep.id_worker" +
                " order by rep.date_produced desc, rep.time_produced desc"
    )
    fun getSearchReports(): List<RecordSearchOut>
}