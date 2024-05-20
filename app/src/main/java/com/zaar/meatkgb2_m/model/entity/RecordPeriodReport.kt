package com.zaar.meatkgb2_m.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopPeriodReport")
data class RecordPeriodReport(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "id_product")
    val idProduct: Long = -1,
    val count: Float = -1f,
    @ColumnInfo(name = "countOfBathes")
    /**
     * How many batches of goods were produced by the reporting period
     * (кол-во партий товара за отчетный период)
     */
    val countOfBathes: Int = 0,
    val countDays: Int = 0,
)