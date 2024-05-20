package com.zaar.meatkgb2_m.data

import androidx.room.ColumnInfo

data class RecordPreVSD(
    val id: Long,
    @ColumnInfo(name = "date_produced")
    var date_produced: String,
    @ColumnInfo(name = "productName")
    val productName: String,
    @ColumnInfo(name = "count")
    val m_count: Float,
    @ColumnInfo(name = "time_produced")
    val time_produced: String,
    @ColumnInfo(name = "vsd_isCreated")
    var vsd_isCreated: Boolean,
    @ColumnInfo(name = "vsd_support")
    val vsd_support: Boolean,
    @ColumnInfo(name = "enterpriseId")
    val enterpriseId: String,
)
