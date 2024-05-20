package com.zaar.meatkgb2_m.model.local.api_room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.zaar.meatkgb2_m.model.entity.Shop;

import java.util.List;
@Dao
public interface Shops_dao extends BaseDao<Shop> {
    @Query("select * from Shops order by id")
    List<Shop> getAllShopsClass();

    @Query("select name from Shops where nonManufacture_isChecked=0 order by lower(name) ASC")
    List<String> getAllManufacture_names();

    @Query("select * from Shops where name = :name limit 1")
    Shop getShopByName(String name);

    @Query("select * from Shops where id = :id")
    Shop getShopByID(long id);

    @Query("select name from Shops where id = :id")
    String getNameShopById(long id);

    @Query("select id from Shops where name=:name_income limit 1")
    long getIdShopByName(String name_income);

    @Query("select id_type_role from Shops where name=:nameShop limit 1")
    int getIdRoleShop(String nameShop);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Shop shop);

    @Query("delete from Shops")
    void deleteAll();

    @Transaction
    default long[] insertWithReplace(List<Shop> shops) {
        deleteAll();
        return insert(shops);
    }
}