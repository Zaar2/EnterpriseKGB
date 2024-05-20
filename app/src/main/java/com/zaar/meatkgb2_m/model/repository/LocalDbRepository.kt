package com.zaar.meatkgb2_m.model.repository

import com.zaar.meatkgb2_m.data.AppUser
import com.zaar.meatkgb2_m.data.NameWorker
import com.zaar.meatkgb2_m.data.RecordPreVSD
import com.zaar.meatkgb2_m.data.RecordSearchOut
import com.zaar.meatkgb2_m.data.RecordShopReportOut
import com.zaar.meatkgb2_m.data.Workers_shortDescription
import com.zaar.meatkgb2_m.model.entity.Enterprise
import com.zaar.meatkgb2_m.model.entity.PreVsd
import com.zaar.meatkgb2_m.model.entity.Product
import com.zaar.meatkgb2_m.model.entity.RecordDailyReport
import com.zaar.meatkgb2_m.model.entity.RecordPeriodReport
import com.zaar.meatkgb2_m.model.entity.RecordSearch
import com.zaar.meatkgb2_m.model.entity.Role
import com.zaar.meatkgb2_m.model.entity.Shop
import com.zaar.meatkgb2_m.model.entity.User
import com.zaar.meatkgb2_m.utilities.types.TypeReports

interface LocalDbRepository {
    fun clearDB()

    //region------------------appUsers
    fun getAppUser(login: String): AppUser
    fun checkAppUser(login: String): Boolean
    fun enablingAppUser(appUser: AppUser): Boolean

    //endregion
    //region------------------Enterprise
    fun addEnterprise(enterprise: Enterprise)

    /**
     * add with pre-deleting record of this entities type
     *
     * @param enterprise enterprise for add
     */
    fun addEnterprise_replaceData(enterprise: Enterprise)
    fun deleteAllEnterprise(): Int
    fun delEnterprise(enterprise: Enterprise): Int
    fun updEnterprise(enterprise: Enterprise): Int
    fun getEnterprise(): Enterprise
    fun getNameEnterprise(id: String): String
    fun getCryptoIdEnterprise(): String

    //endregion
    //region------------------Worker
    fun addWorker(user: User): Long
    fun addWorker(users: List<User>): LongArray

    /**
     * add with pre-deleting all records of this entities type
     *
     * @param users list shops for add
     * @return index list for added records
     */
    fun addWorkers_replaceData(users: List<User>): LongArray
    fun delWorker(user: User): Int
    fun updWorker(worker: User): Int
    fun getAllUsersString(): List<NameWorker>
    fun getAllUsersClass(): List<User>
    fun getWorkersShortDescription(): List<Workers_shortDescription>
    fun getCountUsers(): Int
    fun getWorkerById(id: Long): User
    fun getIdUserByLogin(login: String): Long

    //endregion
    //region------------------Shops
    fun addShop(shop: Shop): Long
    fun addShop(shops: List<Shop>): LongArray

    /**
     * add with pre-deleting all records of this entities type
     *
     * @param shops list shops for add
     * @return index list for added records
     */
    fun addShop_replaceData(shops: List<Shop>): LongArray
    fun delShop(shop: Shop): Int
    fun updShop(shop: Shop): Int
    fun deleteAllShops(): Int
    fun getAllShops(): List<Shop>
    fun getAllManufactureShops(): List<String>
    fun getShopByName(name: String): Shop
    fun getShopByID(id: Long): Shop
    fun getNameShopByID(id: Long): String
    fun getIdShopByName(name: String): Long
    fun getRoleShop(nameShop: String): Int

    //endregion
    //region------------------Products
    fun getProductByName(name: String): Product
    fun getProductById(id: Long): Product
    fun getIdProductByName(name: String): Long
    fun getAllNamesProductByShop(id_workshop: Int): List<String>
    fun getAllNamesProduct(): List<String>
    fun getMapNameIdByName(listProductNames: List<String>): Map<String, Long>
    fun addProduct(product: Product): Long
    fun addProduct_replaceData(products: List<Product>): LongArray
    fun updProduct(product: Product): Int
    fun getIdWorkshopByNameProduct(name: String): Long
    fun deleteById(id: Long): Int

    //endregion
    //region------------------Roles
    fun addRoles_replaceData(roles: List<Role>): LongArray
    fun getAllRolesClass(): List<Role>
    fun getIdRoleByName(name: String): Long
    fun getNameRoleById(id: Long): String

    //endregion
    //region------------------REPORTS
    fun deleteReportShops(typeReports: TypeReports): Int
    fun deleteReportPreVsd(): Int
    fun deleteReportSearch(): Int
    fun addDailyReportShop(report: List<RecordDailyReport>): LongArray
    fun addPeriodReportShop(report: List<RecordPeriodReport>): LongArray
    fun addPreVsd(report: List<PreVsd>): LongArray
    fun addReportSearch(report: List<RecordSearch>): LongArray
    fun getAllRecordsOfReport(typeReports: TypeReports): List<RecordShopReportOut>
    fun getAllRecordsPreVsd(): List<RecordPreVSD>
    fun getAllRecordsSearch(): List<RecordSearchOut>
    fun getListWorkers(type: TypeReports): List<String> //endregion
}