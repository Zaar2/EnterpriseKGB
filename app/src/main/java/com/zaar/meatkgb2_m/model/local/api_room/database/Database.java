package com.zaar.meatkgb2_m.model.local.api_room.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.zaar.meatkgb2_m.model.entity.RecordSearch;
import com.zaar.meatkgb2_m.model.local.api_room.dao.PreVsdDao;
import com.zaar.meatkgb2_m.model.local.api_room.dao.Products_dao;
import com.zaar.meatkgb2_m.model.local.api_room.dao.ReportSearch;
import com.zaar.meatkgb2_m.model.local.api_room.dao.Roles_dao;
import com.zaar.meatkgb2_m.model.local.api_room.dao.ShopDailyReportDao;
import com.zaar.meatkgb2_m.model.local.api_room.dao.ShopPeriodReportDao;
import com.zaar.meatkgb2_m.model.local.api_room.dao.User_dao;
import com.zaar.meatkgb2_m.model.local.api_room.dao.Enterprise_dao;
import com.zaar.meatkgb2_m.model.entity.Enterprise;
import com.zaar.meatkgb2_m.model.entity.PreVsd;
import com.zaar.meatkgb2_m.model.entity.Product;
import com.zaar.meatkgb2_m.model.entity.RecordDailyReport;
import com.zaar.meatkgb2_m.model.entity.RecordPeriodReport;
import com.zaar.meatkgb2_m.model.entity.Role;
import com.zaar.meatkgb2_m.model.entity.Shop;
import com.zaar.meatkgb2_m.model.local.api_room.dao.Shops_dao;
import com.zaar.meatkgb2_m.model.entity.User;
import com.zaar.meatkgb2_m.utilities.Const;
@androidx.room.Database(
        version = 3,
        entities = {
                Shop.class,
                Enterprise.class,
                User.class,
                Product.class,
                Role.class,
                RecordDailyReport.class,
                RecordPeriodReport.class,
                PreVsd.class,
                RecordSearch.class
        })
public abstract class Database extends RoomDatabase {

    private static final String DATABASE_NAME = Const.NAME_DATABASE;
    private static volatile Database INSTANCE;

    public abstract Shops_dao shops_dao();
    public abstract Enterprise_dao enterprise_dao();
    public abstract User_dao user_dao();
    public abstract Products_dao products_dao();
    public abstract Roles_dao rolesDao();
    public abstract ShopDailyReportDao shopDailyReportDao();
    public abstract ShopPeriodReportDao shopPeriodReportDao();
    public abstract PreVsdDao preVsdDao();
    public abstract ReportSearch reportSearch();

    private static final Object LOCK = new Object();

    public static Database getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    public static final Migration MIGRATION=new Migration(MIGRATION.startVersion, MIGRATION.endVersion) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//
//        }
//    }
}