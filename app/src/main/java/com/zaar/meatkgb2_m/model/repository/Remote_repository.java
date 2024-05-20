package com.zaar.meatkgb2_m.model.repository;

import com.zaar.meatkgb2_m.data.LogPass;
import com.zaar.meatkgb2_m.data.RecordsOfProduced;
import com.zaar.meatkgb2_m.model.entity.Enterprise;
import com.zaar.meatkgb2_m.model.entity.PreVsd;
import com.zaar.meatkgb2_m.model.entity.Product;
import com.zaar.meatkgb2_m.model.entity.RecordDailyReport;
import com.zaar.meatkgb2_m.model.entity.RecordPeriodReport;
import com.zaar.meatkgb2_m.model.entity.RecordSearch;
import com.zaar.meatkgb2_m.model.entity.Role;
import com.zaar.meatkgb2_m.model.entity.Shop;
import com.zaar.meatkgb2_m.model.entity.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;

interface Remote_repository {
    Call<String> identificationUser(LogPass logPass);

    Observable<String> identificationUser_rx(LogPass logPass);

    Observable<String> identificationSession(String sessionID);


    //-----ENTERPRISE
    Observable<String> addEnterprise(Enterprise enterprise);

    Call<String> delEnterprise(String id);

    Single<List<Enterprise>> getEnterprise(String id);

    Call<String> updEnterprise(Enterprise enterprise);


    //------SHOPS
    Call<List<Shop>> addShop(Shop shop);

    Single<List<Shop>> getShops(String idEnterprise);

    Call<Long> delShop(long id);

    Call<Long> updShop(Shop shop);


    //------WORKERS
    Call<List<User>> addWorker(User user);

    Single<List<User>> getWorkers(String idEnterprise);

    Call<Long> delWorker(long id);

    Call<Long> updWorker(User Worker);


    //------PRODUCT
    Call<List<Product>> addProduct(Product product);

    Single<List<Product>> getProduct(String idEnterprise);

    Call<Long> delProduct(long id);

    Call<Long> updProduct(Product product);


    //------ROLES
    Single<List<Role>> getRoles();


    //------REPORTS
    Single<List<RecordDailyReport>> getDailyShopReport(String type, String firstDay, long idShop, String enterpriseId);

    Single<List<RecordPeriodReport>> getPeriodShopReport(String type, String firstDay, String lastDay, long idShop, String enterpriseId);

    Single<List<PreVsd>> getReportForPreVSD(String enterpriseId, String firstDay, String lastDay);
    Single<Integer> setVsdIsCreated(String body);

    Single<List<RecordSearch>> getReportSearchByProduct(String firstDate, String lastDate, Long productId, String enterpriseId);
    Single<String> getStringSearchByProduct(String firstDate, String lastDate, Long productId, String enterpriseId);
}