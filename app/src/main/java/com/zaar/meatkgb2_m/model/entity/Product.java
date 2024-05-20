package com.zaar.meatkgb2_m.model.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@Entity(
        tableName = "Products"
)
public class Product {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String enterpriseId;
    private String product_name;
    private String me;
    private int id_workshop;
    private int accuracy;
    private int vsd_support;
    private String date_created;
    private int id_creator_user;
    private int id_status;

    @Ignore
    public Product() {
    }

    public Product(String enterpriseId,
                   String product_name,
                   String me,
                   int id_workshop,
                   int accuracy,
                   int vsd_support,
                   String date_created,
                   int id_creator_user,
                   int id_status) {
        this.enterpriseId = enterpriseId;
        this.product_name = product_name;
        this.me = me;
        this.id_workshop = id_workshop;
        this.accuracy = accuracy;
        this.vsd_support = vsd_support;
        this.date_created = date_created;
        this.id_creator_user = id_creator_user;
        this.id_status = id_status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public int getId_workshop() {
        return id_workshop;
    }

    public void setId_workshop(int id_workshop) {
        this.id_workshop = id_workshop;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getVsd_support() {
        return vsd_support;
    }

    public void setVsd_support(int vsd_support) {
        this.vsd_support = vsd_support;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
    public void setDateCreatedIsCurrent(){
        this.date_created = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()))
                .format(Calendar.getInstance().getTime());
    }

    public int getId_creator_user() {
        return id_creator_user;
    }

    public void setId_creator_user(int id_creator_user) {
        this.id_creator_user = id_creator_user;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }
}