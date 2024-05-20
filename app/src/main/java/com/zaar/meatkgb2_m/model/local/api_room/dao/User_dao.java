package com.zaar.meatkgb2_m.model.local.api_room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.zaar.meatkgb2_m.data.AppUser;
import com.zaar.meatkgb2_m.data.NameWorker;
import com.zaar.meatkgb2_m.data.Workers_shortDescription;
import com.zaar.meatkgb2_m.model.entity.User;

import java.util.List;

@Dao
public interface User_dao extends BaseDao<User> {
    @Query("select * from Workers order by full_name ASC,  id_workshop ASC")
    List<User> getAllUsersClass();

    @Query("select full_name, short_name from Workers order by full_name asc")
    List<NameWorker> getAllUsersString();

    @Query("select id, full_name, id_workshop, appointment from Workers order by full_name asc, appointment asc")
    List<Workers_shortDescription> getWorkersShortDescription();

    @Query("select appointment, full_name from Workers where usr_login=:login")
    AppUser getAppUser(String login);

    @Query("select exists (select appointment, full_name from Workers where usr_login=:login)")
    boolean checkAppUser(String login);

//    LogPass getLogPasUsr()

    @Query("select count(*) from Workers")
    int getCountUsers();

    @Query("select * " +
            "from Workers " +
            "where id=:id")
    User getUserById(long id);

    @Query("select id from Workers where usr_login=:login")
    long getIdByLogin(String login);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Query("delete from Workers")
    void deleteAll();

    @Transaction
    default long[] insertWithReplace(List<User> users) {
        deleteAll();
        return insert(users);
    }
}