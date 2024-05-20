package com.zaar.meatkgb2_m.model.utils

import com.zaar.meatkgb2_m.data.RecordPreVSD
import com.zaar.meatkgb2_m.model.entity.PreVsd

class ConvertTypes {

    /**
     * (not checked for work)
     * @param incomeListRecords incoming list of class instance to be converting into the PreVsd class
     * @param incomeMapShortDescription key->nameProduct,  value->idProduct
     */
    fun recordPreVsdToPreVsd(
        incomeListRecords: List<RecordPreVSD>,
        incomeMapShortDescription: Map<String, Long>
    ): List<PreVsd> {
        val outcomeListPreVsd: MutableList<PreVsd> = mutableListOf()
        incomeListRecords.forEach {
            outcomeListPreVsd.add(
                PreVsd(
                    it.id,
                    incomeMapShortDescription[it.productName] ?: -1L,
                    it.date_produced,
                    it.m_count,
                    it.time_produced.toInt(),
                    if (it.vsd_isCreated) 1 else 0,
                    if (it.vsd_support) 1 else 0,
                    it.enterpriseId
                )
            )
        }
        return outcomeListPreVsd
    }
}