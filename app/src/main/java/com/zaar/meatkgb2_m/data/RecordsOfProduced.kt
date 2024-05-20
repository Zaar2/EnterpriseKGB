package com.zaar.meatkgb2_m.data

import androidx.room.ColumnInfo

data class RecordsOfProduced(
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "date_produced")
    val dateProduced: String = "non",
    @ColumnInfo(name = "full_name")
    val workerName: String = "non",
    @ColumnInfo(name = "product_name")
    val productName: String = "non",
    val count: String = "non",
    @ColumnInfo(name = "time_produced")
    val timeProduced: String = "non",
)
