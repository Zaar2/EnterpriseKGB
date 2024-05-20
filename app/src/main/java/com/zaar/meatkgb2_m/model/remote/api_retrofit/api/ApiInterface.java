package com.zaar.meatkgb2_m.model.remote.api_retrofit.api;

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
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //region--------getting sessionID
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("identification")
    Call<String> identificationUser(@Body LogPass logPass);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("identification")
    Observable<String> identificationUser_rx(@Body LogPass logPass);

    @GET("session/{sessionID}")
    Observable<String> identificationSession(
            @Path(value = "sessionID") String sessionID
    );
    //endregion


    //region--------ENTERPRISE

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("enterprise")
    Observable<String> addEnterprise(
            @Body Enterprise enterprise
    );

    @GET("enterprise/{sessionID}")
    Single<List<Enterprise>> getEnterprise(
            @Path(value = "sessionID") String sessionID,
            @Query("cryptoID") String enterpriseID
    );

    @DELETE("enterprise/{enterpriseID}/{sessionID}")
    Call<String> delEnterprise(
            @Path(value = "enterpriseID") String enterpriseID,
            @Path(value = "sessionID") String sessionID
    );

    @PUT("enterprise/{sessionID}")
    Call<String> updEnterprise(
            @Body Enterprise enterprise,
            @Path(value = "sessionID") String sessionID
    );
    //endregion


    //region--------SHOPS
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("shop/{sessionID}")
    Call<List<Shop>> addShop(
            @Path(value = "sessionID") String sessionID,
            @Body Shop shop
    );

    @GET("shop/{sessionID}")
    Single<List<Shop>> getShops(
            @Path(value = "sessionID") String sessionID,
            @Query("enterpriseId") String enterpriseID
    );

    @DELETE("shop/{shopID}/{sessionID}")
    Call<Long> delShop(
            @Path(value = "shopID") long shopID,
            @Path(value = "sessionID") String sessionID
    );

    @PUT("shop/{sessionID}")
    Call<Long> updShop(
            @Body Shop shop,
            @Path(value = "sessionID") String sessionID
    );
    //endregion


    //region--------WORKERS
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("worker/{sessionID}")
    Call<List<User>> addWorker(
            @Path(value = "sessionID") String sessionID,
            @Body User user
    );

    @GET("worker/{sessionID}")
    Single<List<User>> getWorkers(
            @Path(value = "sessionID") String sessionID,
            @Query("enterpriseId") String enterpriseID
    );

    @DELETE("worker/{workerID}/{sessionID}")
    Call<Long> delWorker(
            @Path(value = "workerID") long workerID,
            @Path(value = "sessionID") String sessionID
    );

    @PUT("worker/{sessionID}")
    Call<Long> updWorker(
            @Path(value = "sessionID") String sessionID,
            @Body User worker
    );
    //endregion


    //region--------PRODUCTS
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("product/{sessionID}")
    Call<List<Product>> addProduct(
            @Path(value = "sessionID") String sessionID,
            @Body Product product
    );

    @GET("product/{sessionID}")
    Single<List<Product>> getProduct(
            @Path(value = "sessionID") String sessionID,
            @Query("enterpriseId") String enterpriseID
    );

    @DELETE("product/{productID}/{sessionID}")
    Call<Long> delProduct(
            @Path(value = "sessionID") String sessionID,
            @Path(value = "productID") long productID
    );

    @PUT("product/{sessionID}")
    Call<Long> updProduct(
            @Path(value = "sessionID") String sessionID,
            @Body Product product
    );
    //endregion


    //region--------ROLES
    @GET("roles/{sessionID}")
    Single<List<Role>> getRoles(
            @Path(value = "sessionID") String sessionID
    );
    //endregion


    //region--------REPORTS
    @GET("reportShops/{sessionID}")
    Single<List<RecordDailyReport>> getDailyReportShop(
            @Path(value = "sessionID") String sessionID,
            @Query("type") String type,
            @Query("firstDay") String firstDay,
            @Query("lastDay") String lastDay,
            @Query("idShop") long idShop,
            @Query("enterpriseId") String enterpriseId
    );

    @GET("reportShops/{sessionID}")
    Single<List<RecordPeriodReport>> getPeriodReportShop(
            @Path(value = "sessionID") String sessionID,
            @Query("type") String type,
            @Query("firstDay") String firstDay,
            @Query("lastDay") String lastDay,
            @Query("idShop") long idShop,
            @Query("enterpriseId") String enterpriseId
    );

    @GET("reportsVSD/{sessionID}")
    Single<List<PreVsd>> getReportForPreVSD(
            @Path(value = "sessionID") String sessionID,
            @Query("enterpriseId") String enterpriseId,
            @Query("firstDay") String firstDay,
            @Query("lastDay") String lastDay
    );

    @PUT("reportsVSD/{sessionID}")
    Single<Integer> setVsdIsCreated(
            @Path(value = "sessionID") String sessionID,
            @Body String body
    );

    @GET("reportSearch/byProduct/{sessionID}")
    Single<List<RecordSearch>> getReportSearchByProduct(
            @Path(value = "sessionID") String sessionID,
            @Query("firstDay") String firstDay,
            @Query("lastDay") String lastDay,
            @Query("idProduct") Long idProduct,
            @Query("enterpriseId") String enterpriseId
    );

    @GET("reportSearch/byProduct/{sessionID}")
    Single<String> getStringSearchByProduct(
            @Path(value = "sessionID") String sessionID,
            @Query("firstDay") String firstDay,
            @Query("lastDay") String lastDay,
            @Query("idProduct") Long idProduct,
            @Query("enterpriseId") String enterpriseId
    );
    //endregion
}