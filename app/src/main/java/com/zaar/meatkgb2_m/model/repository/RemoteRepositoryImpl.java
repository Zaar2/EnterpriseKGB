package com.zaar.meatkgb2_m.model.repository;

import com.zaar.meatkgb2_m.data.LogPass;
import com.zaar.meatkgb2_m.data.RecordsOfProduced;
import com.zaar.meatkgb2_m.model.entity.RecordSearch;
import com.zaar.meatkgb2_m.model.local.EnabledAppUser;
import com.zaar.meatkgb2_m.model.entity.PreVsd;
import com.zaar.meatkgb2_m.model.entity.Product;
import com.zaar.meatkgb2_m.model.entity.RecordDailyReport;
import com.zaar.meatkgb2_m.model.entity.RecordPeriodReport;
import com.zaar.meatkgb2_m.model.entity.Role;
import com.zaar.meatkgb2_m.model.remote.api_retrofit.api.ApiInterface;
import com.zaar.meatkgb2_m.model.remote.api_retrofit.builder.ApiClient;
import com.zaar.meatkgb2_m.model.entity.Enterprise;
import com.zaar.meatkgb2_m.model.entity.Shop;
import com.zaar.meatkgb2_m.model.entity.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;

public class RemoteRepositoryImpl implements Remote_repository {

    //region--------getting sessionID
    @Override
    public Call<String> identificationUser(LogPass logPass) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return apiService.identificationUser(logPass);
    }

    @Override
    public Observable<String> identificationUser_rx(LogPass logPass) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return apiService.identificationUser_rx(logPass);
    }

    @Override
    public Observable<String> identificationSession(String sessionID) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return apiService.identificationSession(sessionID);
    }
    //endregion


    //region------------------Enterprise
    @Override
    public Observable<String> addEnterprise(Enterprise enterprise) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return apiService.addEnterprise(enterprise);
    }

    @Override
    public Call<String> delEnterprise(String id) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return apiService.delEnterprise(id, EnabledAppUser.getINSTANCE().getSessionID());
    }

    @Override
    public Single<List<Enterprise>> getEnterprise(String id) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String sessionID = EnabledAppUser.getINSTANCE().getSessionID();
        return apiService.getEnterprise(sessionID, id);
    }

    @Override
    public Call<String> updEnterprise(Enterprise enterprise) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String sessionID = EnabledAppUser.getINSTANCE().getSessionID();
        return apiService.updEnterprise(enterprise, sessionID);
    }
    //endregion


    //region------------------Shops
    @Override
    public Call<List<Shop>> addShop(Shop shop) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return apiService.addShop(EnabledAppUser.getINSTANCE().getSessionID(), shop);
    }

    @Override
    public Single<List<Shop>> getShops(String idEnterprise) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String sessionID = EnabledAppUser.getINSTANCE().getSessionID();
        return apiService.getShops(sessionID, idEnterprise);
    }

    @Override
    public Call<Long> delShop(long shopID) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String sessionID = EnabledAppUser.getINSTANCE().getSessionID();
        return apiService.delShop(shopID, sessionID);
    }

    @Override
    public Call<Long> updShop(Shop shop) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String sessionID = EnabledAppUser.getINSTANCE().getSessionID();
        return apiService.updShop(shop, sessionID);
    }
    //endregion


    //region------------------Worker
    @Override
    public Call<List<User>> addWorker(User user) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return apiService.addWorker(EnabledAppUser.getINSTANCE().getSessionID(), user);
    }

    @Override
    public Single<List<User>> getWorkers(String idEnterprise) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return apiService.getWorkers(EnabledAppUser.getINSTANCE().getSessionID(), idEnterprise);
    }

    @Override
    public Call<Long> delWorker(long workerID) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return apiService.delWorker(workerID, EnabledAppUser.getINSTANCE().getSessionID());
    }

    @Override
    public Call<Long> updWorker(User worker) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String sessionID = EnabledAppUser.getINSTANCE().getSessionID();
        return apiService.updWorker(sessionID, worker);
    }
    //endregion


    //region------------------Product
    @Override
    public Call<List<Product>> addProduct(Product product) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return apiService.addProduct(EnabledAppUser.getINSTANCE().getSessionID(), product);
    }

    @Override
    public Single<List<Product>> getProduct(String idEnterprise) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return apiService.getProduct(EnabledAppUser.getINSTANCE().getSessionID(), idEnterprise);
    }

    @Override
    public Call<Long> delProduct(long productID) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return apiService.delProduct(EnabledAppUser.getINSTANCE().getSessionID(), productID);
    }

    @Override
    public Call<Long> updProduct(Product product) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return apiService.updProduct(EnabledAppUser.getINSTANCE().getSessionID(), product);
    }
    //endregion


    //region------------------Roles
    @Override
    public Single<List<Role>> getRoles() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String sessionID = EnabledAppUser.getINSTANCE().getSessionID();
        return apiService.getRoles(sessionID);
    }
    //endregion


    //region------------------REPORTS
    @Override
    public Single<List<RecordDailyReport>> getDailyShopReport(
            String type, String firstDay, long idShop, String enterpriseId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String sessionID = EnabledAppUser.getINSTANCE().getSessionID();
        return apiService.getDailyReportShop(
                sessionID, type, firstDay, "non", idShop, enterpriseId);
    }

    @Override
    public Single<List<RecordPeriodReport>> getPeriodShopReport(
            String type, String firstDay, String lastDay, long idShop, String enterpriseId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String sessionID = EnabledAppUser.getINSTANCE().getSessionID();
        return apiService.getPeriodReportShop(
                sessionID, type, firstDay, lastDay, idShop, enterpriseId);
    }

    @Override
    public Single<List<PreVsd>> getReportForPreVSD(
            String enterpriseId, String firstDay, String lastDay) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String sessionID = EnabledAppUser.getINSTANCE().getSessionID();
        return apiService.getReportForPreVSD(sessionID, enterpriseId, firstDay, lastDay);
    }

    @Override
    public Single<Integer> setVsdIsCreated(String body) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String sessionID = EnabledAppUser.getINSTANCE().getSessionID();
        return apiService.setVsdIsCreated(sessionID, body);
    }

    @Override
    public Single<List<RecordSearch>> getReportSearchByProduct(
            String firstDate, String lastDate, Long productId, String enterpriseId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String sessionID = EnabledAppUser.getINSTANCE().getSessionID();
        return apiService.getReportSearchByProduct(
                sessionID, firstDate, lastDate, productId, enterpriseId);
    }

    @Override
    public Single<String> getStringSearchByProduct(String firstDate, String lastDate, Long productId, String enterpriseId) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            String sessionID = EnabledAppUser.getINSTANCE().getSessionID();
            return apiService.getStringSearchByProduct(
                    sessionID, firstDate, lastDate, productId, enterpriseId);
    }

    //endregion
}