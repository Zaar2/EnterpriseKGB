package com.zaar.meatkgb2_m.model.local.api_room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


import com.zaar.meatkgb2_m.data.ProductIdName;
import com.zaar.meatkgb2_m.model.entity.Product;

import java.util.List;

@Dao
public interface Products_dao extends BaseDao<Product> {

    @Query("select * from Products")
    List<Product> getAllProductClass();

    @Query("select product_name from Products where id_workshop=:id_workshop order by product_name")
    List<String> getAllNamesProductByShop(int id_workshop);

    @Query("select product_name from Products order by product_name")
    List<String> getAllNamesProduct();

    @Query("select id_workshop from Products where id=:idProduct limit 1")
    int getIdWorkshopByNameProduct(long idProduct);

    @Query("select id_workshop from Products where product_name=:nameProduct limit 1")
    long getIdWorkshopByNameProduct(String nameProduct);

    @Query("select * from Products where product_name = :name limit 1")
    Product getProductByName(String name);

    @Query("select * from Products where id = :id")
    Product getProductByID(long id);

    @Query("select product_name from Products where id = :id")
    String getNameProductById(long id);

    @Query("select id from Products where product_name=:name_income")
    long getIdByName(String name_income);

    @Query("select id from Products where product_name=:name_income")
    Long[] getIdsByName(String name_income);

    @Query("select id, product_name from Products where product_name=:name")
    List<ProductIdName> getProductIdNameByName(List<String> name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Product product);

    @Query("delete from Products")
    void deleteAll();

    @Query("delete from Products where id=:id")
    int deleteById(long id);

    @Transaction
    default long[] insertWithReplace(List<Product> products) {
        deleteAll();
        return insert(products);
    }
}
