package com.zaar.meatkgb2_m.model.repository

import android.content.Context
import android.os.AsyncTask
import com.zaar.meatkgb2_m.data.AppUser
import com.zaar.meatkgb2_m.data.NameWorker
import com.zaar.meatkgb2_m.data.ProductIdName
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
import com.zaar.meatkgb2_m.model.local.EnabledAppUser
import com.zaar.meatkgb2_m.model.local.api_room.database.Database
import com.zaar.meatkgb2_m.utilities.types.TypeReports
import com.zaar.meatkgb2_m.utilities.viewModel.VMUtilities
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.function.Consumer

class LocalDbRepositoryImpl(context: Context) : LocalDbRepository {
    var db: Database

    init {
        db = Database.getINSTANCE(context)
    }

    override fun clearDB() {
        AsyncTask.execute {
            db.runInTransaction {
                try {
                    db.user_dao().deleteAll()
                } catch (exception: Exception) {
                    exception.printStackTrace()
                } finally {
                    try {
                        db.shops_dao().deleteAll()
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    } finally {
                        try {
                            db.enterprise_dao().deleteAll()
                        } catch (exception: Exception) {
                            exception.printStackTrace()
                        } finally {
                            try {
                                db.products_dao().deleteAll()
                            } catch (exception: Exception) {
                                exception.printStackTrace()
                            } finally {
                                try {
                                    db.rolesDao().deleteAll()
                                } catch (exception: Exception) {
                                    exception.printStackTrace()
                                } finally {
                                    try {
                                        db.shopDailyReportDao().deleteAll()
                                    } catch (exception: Exception) {
                                        exception.printStackTrace()
                                    } finally {
                                        try {
                                            db.shopPeriodReportDao().deleteAll()
                                        } catch (exception: Exception) {
                                            exception.printStackTrace()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //region------------------appUsers
    override fun enablingAppUser(appUser: AppUser): Boolean {
        EnabledAppUser.getINSTANCE().enterpriseName = appUser.enterpriseName
        EnabledAppUser.getINSTANCE().usrFullName = appUser.usrFullName
        EnabledAppUser.getINSTANCE().appointment = appUser.appointment
        EnabledAppUser.getINSTANCE().usrLogin = appUser.usrLogin
        return EnabledAppUser.getINSTANCE().enterpriseName != "" ||
                EnabledAppUser.getINSTANCE().usrFullName != "" ||
                EnabledAppUser.getINSTANCE().appointment != ""
    }

    //endregion
    //region------------------Enterprise
    override fun addEnterprise_replaceData(enterprise: Enterprise) {
        Executors.newSingleThreadExecutor()
            .execute { db.enterprise_dao().insertWithReplace(enterprise) }
    }

    override fun addEnterprise(enterprise: Enterprise) {
        Executors.newSingleThreadExecutor().execute { db.enterprise_dao().insert(enterprise) }
    }

    override fun delEnterprise(enterprise: Enterprise): Int {
        return 0
    }

    override fun deleteAllEnterprise(): Int {
        var count = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Int> { db.enterprise_dao().deleteAll() }
        try {
            count = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return count
    }

    override fun updEnterprise(enterprise: Enterprise): Int {
        var count = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Int> { db.enterprise_dao().update(enterprise) }
        try {
            count = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return count
    }

    override fun getEnterprise(): Enterprise {
        var enterprise = Enterprise()
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<List<Enterprise>> { db.enterprise_dao().getEnterprise() }
        try {
            enterprise = result.get()[0]
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return enterprise
    }

    override fun getNameEnterprise(id: String): String {
        var name = ""
        val es = Executors.newSingleThreadExecutor()
        val names = es.submit<List<String>> { db.enterprise_dao().getNameEnterprise(id) }
        try {
            name = names.get()[0]
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return name
    }

    override fun getCryptoIdEnterprise(): String {
        var name = "";
        val es = Executors.newSingleThreadExecutor();
        val names: Future<List<String>> =
            es.submit<List<String>> { db.enterprise_dao().cryptoIdEnterprise }
        try {
            name = names.get()[0];
        } catch (exception: Exception) {
            exception.printStackTrace();
        }
        es.shutdown();
        return name
    }

    //endregion
    //region------------------Worker
    override fun addWorker(user: User): Long {
        var id: Long = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Long> { db.user_dao().insert(user) }
        try {
            id = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return id
    }

    override fun addWorker(users: List<User>): LongArray {
        var id = LongArray(users.size)
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<LongArray> { db.user_dao().insert(users) }
        try {
            id = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return id
    }

    override fun addWorkers_replaceData(users: List<User>): LongArray {
//        Executors.newSingleThreadExecutor().execute(() -> db.user_dao().deleteAll());
        var id = LongArray(users.size)
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<LongArray> { db.user_dao().insertWithReplace(users) }
        try {
            id = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return id
    }

    override fun delWorker(user: User): Int {
        var count = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Int> { db.user_dao().delete(user) }
        try {
            count = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return count
    }

    override fun updWorker(worker: User): Int {
        var count = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Int> { db.user_dao().update(worker) }
        try {
            count = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return count
    }

    override fun getAllUsersString(): List<NameWorker> {
        val nameUsers: MutableList<NameWorker> = ArrayList()
        nameUsers.add(NameWorker())
        return nameUsers
    }

    override fun getAllUsersClass(): List<User> {
        var users: List<User> = ArrayList()
        val executorService = Executors.newSingleThreadExecutor()
        val result = executorService.submit<List<User>> { db.user_dao().allUsersClass }
        try {
            users = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        executorService.shutdown()
        return users
    }

    override fun getWorkersShortDescription(): List<Workers_shortDescription> {
        var workersShortDescriptions: List<Workers_shortDescription> = ArrayList()
        val executorService = Executors.newSingleThreadExecutor()
        val result =
            executorService.submit<List<Workers_shortDescription>> { db.user_dao().workersShortDescription }
        try {
            workersShortDescriptions = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        executorService.shutdown()
        return workersShortDescriptions
    }

    override fun getCountUsers(): Int {
        var count = -1
        val executorService = Executors.newSingleThreadExecutor()
        val result = executorService.submit<Int> { db.user_dao().countUsers }
        try {
            count = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        executorService.shutdown()
        return count
    }

    override fun getWorkerById(id: Long): User {
        var user = User()
        val executorService = Executors.newSingleThreadExecutor()
        val result = executorService.submit<User> { db.user_dao().getUserById(id) }
        try {
            user = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        executorService.shutdown()
        return user
    }

    override fun getIdUserByLogin(login: String): Long {
        var id = -1L
        val executorService = Executors.newSingleThreadExecutor()
        val result = executorService.submit<Long> { db.user_dao().getIdByLogin(login) }
        try {
            id = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        executorService.shutdown()
        return id
    }

    override fun getAppUser(login: String): AppUser {
        var user = AppUser()
        val executorService = Executors.newSingleThreadExecutor()
        val result = executorService.submit<AppUser> { db.user_dao().getAppUser(login) }
        try {
            user = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        executorService.shutdown()
        return user
    }

    override fun checkAppUser(login: String): Boolean {
        var result = false
        val executorService = Executors.newSingleThreadExecutor()
        val response = executorService.submit<Boolean> { db.user_dao().checkAppUser(login) }
        try {
            result = response.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            executorService.shutdown()
        }
        return result
    }

    override fun getIdWorkshopByNameProduct(name: String): Long {
        var idWorkshop = -1L
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Long> { db.products_dao().getIdWorkshopByNameProduct(name) }
        try {
            idWorkshop = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return idWorkshop
    }

    //endregion
    //region------------------Shops
    override fun getAllShops(): List<Shop> {
        var shops: List<Shop> = ArrayList()
        val executorService = Executors.newSingleThreadExecutor()
        val result = executorService.submit<List<Shop>> { db.shops_dao().allShopsClass }
        try {
            shops = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        executorService.shutdown()
        return shops
    }

    override fun getAllManufactureShops(): List<String> {
        var shops: List<String> = ArrayList()
        val executorService = Executors.newSingleThreadExecutor()
        val result = executorService.submit<List<String>> { db.shops_dao().allManufacture_names }
        try {
            shops = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        executorService.shutdown()
        return shops
    }

    override fun addShop(shop: Shop): Long {
        var id: Long = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Long> { db.shops_dao().insert(shop) }
        try {
            id = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return id
    }

    override fun addShop(shops: List<Shop>): LongArray {
        var id = LongArray(shops.size)
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<LongArray> { db.shops_dao().insert(shops) }
        try {
            id = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return id
    }

    override fun addShop_replaceData(shops: List<Shop>): LongArray {
        var id = LongArray(shops.size)
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<LongArray> { db.shops_dao().insertWithReplace(shops) }
        try {
            id = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return id
    }

    override fun delShop(shop: Shop): Int {
        var count = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Int> { db.shops_dao().delete(shop) }
        try {
            count = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return count
    }

    override fun deleteAllShops(): Int {
        var count = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Int> { db.enterprise_dao().deleteAll() }
        try {
            count = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return count
    }

    override fun updShop(shop: Shop): Int {
        var count = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Int> { db.shops_dao().update(shop) }
        try {
            count = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return count
    }

    override fun getShopByName(name: String): Shop {
        var shop = Shop()
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Shop> { db.shops_dao().getShopByName(name) }
        try {
            shop = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return shop
    }

    override fun getShopByID(id: Long): Shop {
        var shop = Shop()
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Shop> { db.shops_dao().getShopByID(id) }
        try {
            shop = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return shop
    }

    override fun getNameShopByID(id: Long): String {
        var name = "non"
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<String> { db.shops_dao().getNameShopById(id) }
        try {
            name = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return name
    }

    override fun getIdShopByName(name: String): Long {
        var id = -1L
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Long> { db.shops_dao().getIdShopByName(name) }
        try {
            id = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return id
    }

    override fun getRoleShop(nameShop: String): Int {
        var id = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Int> { db.shops_dao().getIdRoleShop(nameShop) }
        try {
            id = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            es.shutdown()
        }
        return id
    }

    //endregion
    //region------------------Products
    override fun addProduct(product: Product): Long {
        var id: Long = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Long> { db.products_dao().insert(product) }
        try {
            id = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return id
    }

    override fun updProduct(product: Product): Int {
        var id = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Int> { db.products_dao().update(product) }
        try {
            id = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return id
    }

    override fun addProduct_replaceData(products: List<Product>): LongArray {
        var id = LongArray(products.size)
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<LongArray> { db.products_dao().insertWithReplace(products) }
        try {
            id = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return id
    }

    override fun getProductByName(name: String): Product {
        var product = Product()
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Product> { db.products_dao().getProductByName(name) }
        try {
            product = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return product
    }

    override fun getProductById(id: Long): Product {
        var product = Product()
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Product> { db.products_dao().getProductByID(id) }
        try {
            product = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return product
    }

    override fun getIdProductByName(name: String): Long {
        var id: Long = -1
        val executorService = Executors.newSingleThreadExecutor()
        val result = executorService.submit<Long> { db.products_dao().getIdByName(name) }
        try {
            id = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        executorService.shutdown()
        return id
    }

    override fun getAllNamesProductByShop(id_workshop: Int): List<String> {
        var names: List<String> = ArrayList()
        val executorService = Executors.newSingleThreadExecutor()
        val result = executorService.submit<List<String>> {
            db.products_dao().getAllNamesProductByShop(id_workshop)
        }
        try {
            names = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        executorService.shutdown()
        return names
    }

    override fun getAllNamesProduct(): List<String> {
        var names: List<String> = ArrayList()
        val executorService = Executors.newSingleThreadExecutor()
        val result = executorService.submit<List<String>> { db.products_dao().allNamesProduct }
        try {
            names = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        executorService.shutdown()
        return names
    }

    /**
     * @param listProductNames list names of products
     * @return Map &lt; nameProduct, idProduct &gt;
     */
    override fun getMapNameIdByName(listProductNames: List<String>): Map<String, Long> {
        val names: List<ProductIdName>
        val map: MutableMap<String, Long> = HashMap()
        val executorService = Executors.newSingleThreadExecutor()
        val result = executorService.submit<List<ProductIdName>> {
            db.products_dao().getProductIdNameByName(listProductNames)
        }
        try {
            names = result.get()
            names.forEach(Consumer { (id, name): ProductIdName -> map[name] = id })
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        executorService.shutdown()
        return map
    }

    override fun deleteById(id: Long): Int {
        var count = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Int> { db.products_dao().deleteById(id) }
        try {
            count = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            es.shutdown()
        }
        return count
    }

    //endregion
    //region------------------Roles
    override fun addRoles_replaceData(roles: List<Role>): LongArray {
        var id = LongArray(roles.size)
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<LongArray> { db.rolesDao().insertWithReplace(roles) }
        try {
            id = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            es.shutdown()
        }
        return id
    }

    override fun getAllRolesClass(): List<Role> {
        var roles: List<Role> = ArrayList()
        val executorService = Executors.newSingleThreadExecutor()
        val result = executorService.submit<List<Role>> { db.rolesDao().allRolesClass }
        try {
            roles = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            executorService.shutdown()
        }
        return roles
    }

    override fun getIdRoleByName(name: String): Long {
        var id: Long = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Long> { db.rolesDao().getIdByName(name) }
        try {
            id = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            es.shutdown()
        }
        return id
    }

    override fun getNameRoleById(id: Long): String {
        var name = "non"
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<String> { db.rolesDao().getNameById(id) }
        try {
            name = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            es.shutdown()
        }
        return name
    }

    //endregion
    //region------------------REPORTS
    override fun deleteReportShops(typeReports: TypeReports): Int {
        var count = -1
        val es = Executors.newSingleThreadExecutor()
        val result: Future<Int>?
        result = when (typeReports) {
            TypeReports.SHOP_DAY -> es.submit<Int> { db.shopDailyReportDao().deleteAll() }
            TypeReports.SHOP_PERIOD -> es.submit<Int> { db.shopPeriodReportDao().deleteAll() }
        }
        try {
            if (result != null) {
                count = result.get()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return count
    }

    override fun deleteReportPreVsd(): Int {
        var count = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Int> { db.preVsdDao().deleteAll() }
        try {
            if (result != null) {
                count = result.get()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return count
    }

    override fun deleteReportSearch(): Int {
        var count = -1
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<Int> { db.reportSearch().deleteAll() }
        try {
            if (result != null) {
                count = result.get()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        es.shutdown()
        return count
    }

    override fun addDailyReportShop(report: List<RecordDailyReport>): LongArray {
        val id: LongArray
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<LongArray> { db.shopDailyReportDao().insertWithReplace(report) }
        id = try {
            result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
            LongArray(0)
        } finally {
            es.shutdown()
        }
        return id
    }

    override fun addPeriodReportShop(report: List<RecordPeriodReport>): LongArray {
        val id: LongArray
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<LongArray> { db.shopPeriodReportDao().insertWithReplace(report) }
        id = try {
            result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
            LongArray(0)
        } finally {
            es.shutdown()
        }
        return id
    }

    override fun addPreVsd(report: List<PreVsd>): LongArray {
        val id: LongArray
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<LongArray> { db.preVsdDao().insertWithReplace(report) }
        id = try {
            result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
            LongArray(0)
        } finally {
            es.shutdown()
        }
        return id
    }

    override fun addReportSearch(report: List<RecordSearch>): LongArray {
        val id: LongArray
        val es = Executors.newSingleThreadExecutor()
        val result = es.submit<LongArray> { db.reportSearch().insertWithReplace(report) }
        id = try {
            result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
            LongArray(0)
        } finally {
            es.shutdown()
        }
        return id
    }

    override fun getAllRecordsOfReport(typeReports: TypeReports): List<RecordShopReportOut> {
        var records: List<RecordShopReportOut> = ArrayList()
        val executorService = Executors.newSingleThreadExecutor()
        var result: Future<List<RecordShopReportOut>>? = null
        when (typeReports) {
            TypeReports.SHOP_DAY -> result = executorService.submit<List<RecordShopReportOut>> {
                db.shopDailyReportDao().getDailyReports()
            }

            TypeReports.SHOP_PERIOD -> result = executorService.submit<List<RecordShopReportOut>> {
                db.shopPeriodReportDao().getReportsOfPeriod()
            }

        }
        try {
            if (result != null) {
                records = result.get()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            executorService.shutdown()
        }
        return records
    }

    override fun getAllRecordsPreVsd(): List<RecordPreVSD> {
        var records: List<RecordPreVSD> = ArrayList()
        val executorService = Executors.newSingleThreadExecutor()
        val result = executorService.submit<List<RecordPreVSD>> { db.preVsdDao().getReportPreVsd() }
        try {
            if (result != null) {
                records = result.get()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            executorService.shutdown()
        }
        records.forEach(
            Consumer { recordPreVSD: RecordPreVSD ->
                val local = VMUtilities().convertDateServToLocal(
                    recordPreVSD.date_produced
                )
                if (local != null) recordPreVSD.date_produced = local
            }
        )
        return records
    }

    override fun getAllRecordsSearch(): List<RecordSearchOut> {
        var records: List<RecordSearchOut> = ArrayList()
        val executorService = Executors.newSingleThreadExecutor()
        val result =
            executorService.submit<List<RecordSearchOut>> { db.reportSearch().getSearchReports() }
        try {
            if (result != null) {
                records = result.get()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            executorService.shutdown()
        }
        records.forEach(
            Consumer { RecordSearchOut: RecordSearchOut ->
                val local = VMUtilities().convertDateServToLocal(
                    RecordSearchOut.dateProduced
                )
                if (local != null) RecordSearchOut.dateProduced = local
            }
        )
        return records
    }

    override fun getListWorkers(type: TypeReports): List<String> {
        var workers: List<String>? = null
        val executorService = Executors.newSingleThreadExecutor()
        val result: Future<List<String>>?
        result = when (type) {
            TypeReports.SHOP_DAY -> executorService.submit<List<String>> {
                db.shopDailyReportDao().getListWorkersOfReports()
            }

            TypeReports.SHOP_PERIOD -> null
        }
        try {
            if (result != null) workers = result.get()
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            executorService.shutdown()
        }
        return workers!!
    } //endregion
}