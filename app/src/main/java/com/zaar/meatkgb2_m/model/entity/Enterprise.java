package com.zaar.meatkgb2_m.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Enterprise",
        indices = @Index(value = "enterpriseId",unique = true)
)
public class Enterprise {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "enterpriseId")
    private String enterpriseId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "inn")
    private String inn;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "dateCreated")
    private String dateCreated;

    public Enterprise(String name, String inn, String email) {
        this.enterpriseId = "";
        this.name = name;
        this.inn = inn;
        this.email = email;
    }

    @Ignore
    public Enterprise() {
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
