package com.zaar.meatkgb2_m.data

import androidx.room.ColumnInfo

data class RecordShopReportOut(
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
    @ColumnInfo(name = "countOfBathes")
    /**
     * How many batches of goods were produced by the reporting period
     * (кол-во партий товара за отчетный период)
     */
    val countOfBathes: String = "0",
    val countDays: String = "0",
    val me: String = "non"
)
