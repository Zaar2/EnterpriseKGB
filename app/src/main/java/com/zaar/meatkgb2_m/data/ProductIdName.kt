package com.zaar.meatkgb2_m.data

import androidx.room.ColumnInfo

data class ProductIdName(
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "product_name")
    val name: String
)
