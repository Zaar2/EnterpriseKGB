package com.zaar.meatkgb2_m.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class RecordSearchOut(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "date_produced")
    var dateProduced: String = "non",
    @ColumnInfo(name = "product_name")
    val nameProduct: String = "non",
    val count: Float = -1f,
    @ColumnInfo(name = "time_produced")
    val timeProduced: Byte = -1,
    @ColumnInfo(name = "time_created")
    var timeCreated: String = "non",
    @ColumnInfo(name = "full_name")
    val nameWorker: String = "non",
)
