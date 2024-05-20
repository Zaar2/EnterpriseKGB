package com.zaar.meatkgb2_m.model.local.api_room.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.zaar.meatkgb2_m.model.entity.Role;

import java.util.List;

@Dao
public interface Roles_dao extends BaseDao<Role>{

    @Query("delete from Roles")
    void deleteAll();

    @Transaction
    default long[] insertWithReplace(List<Role> roles) {
        deleteAll();
        return insert(roles);
    }

    @Query("select * from Roles order by id")
    List<Role> getAllRolesClass();

    @Query("select id from Roles where name=:name")
    long getIdByName(String name);

    @Query("select name from Roles where id=:id")
    String getNameById(long id);
}
