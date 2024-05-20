package com.zaar.meatkgb2_m.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searchReport")
data class RecordSearch(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "date_produced")
    val date_produced: String = "non",
    @ColumnInfo(name = "id_product")
    val id_product: Long = -1,
    val count: Float = -1f,
    @ColumnInfo(name = "time_produced")
    val time_produced: Byte = -1,
    @ColumnInfo(name = "time_created")
    val time_created: String,
    @ColumnInfo(name = "id_worker")
    val id_worker: Long = -1,
)
