package com.zaar.meatkgb2_m.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preVsd")
class PreVsd(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "id_product")
    val id_product: Long,
    @ColumnInfo(name = "date_produced")
    val date_produced: String,
    @ColumnInfo(name = "count")
    val m_count: Float,
    @ColumnInfo(name = "time_produced")
    val time_produced: Int,
    @ColumnInfo(name = "vsd_isCreated")
    val vsd_isCreated: Int,
    @ColumnInfo(name = "vsd_support")
    val vsd_support: Int,
    @ColumnInfo(name = "enterpriseId")
    val enterpriseId: String,
) {

}