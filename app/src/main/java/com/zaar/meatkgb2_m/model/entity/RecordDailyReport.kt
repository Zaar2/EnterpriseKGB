package com.zaar.meatkgb2_m.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopDailyReport")
data class RecordDailyReport(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "date_produced")
    val dateProduced: String = "non",
    @ColumnInfo(name = "id_worker")
    val idWorker: Long = -1,
    @ColumnInfo(name = "id_product")
    val idProduct: Long = -1,
    val count: Float = -1f,
    @ColumnInfo(name = "time_produced")
    val timeProduced: Byte = -1,
)