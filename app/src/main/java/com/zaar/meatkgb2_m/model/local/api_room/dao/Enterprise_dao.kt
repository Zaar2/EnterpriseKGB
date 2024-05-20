package com.zaar.meatkgb2_m.model.local.api_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zaar.meatkgb2_m.model.entity.Enterprise

@Dao
interface Enterprise_dao : BaseDao<Enterprise?> {
    @Query("delete from Enterprise")
    fun deleteAll(): Int

    @Query("select * from Enterprise")
    fun getEnterprise(): List<Enterprise>

    @Query("select name from Enterprise where enterpriseId=:id")
    fun getNameEnterprise(id: String): List<String>

    @get:Query("select enterpriseId from Enterprise")
    val cryptoIdEnterprise: List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(enterprise: Enterprise)

    @Transaction
    fun insertWithReplace(enterprise: Enterprise) {
        deleteAll()
        insert(enterprise)
    }
}