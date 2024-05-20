package com.zaar.meatkgb2_m.model.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Shops"
//        foreignKeys = @ForeignKey(
//                entity = Enterprise.class,
//                parentColumns = "cryptoId",
//                childColumns = "enterprise_cryptoId",
//                onDelete = ForeignKey.CASCADE,
//                onUpdate = ForeignKey.CASCADE
//        )
)
public class Shop {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String short_name;
    private String enterpriseId;
    private int nonManufacture_isChecked;
    private String date_created;
    private long id_type_role;
    private int id_status;
    @Ignore
    private String nameRole;

    @Ignore
    public Shop() {
    }

    public Shop(String name,
                String short_name,
                String enterpriseId,
                int nonManufacture_isChecked,
                String date_created,
                long id_type_role,
                int id_status) {
        this.name = name;
        this.short_name = short_name;
        this.enterpriseId = enterpriseId;
        this.nonManufacture_isChecked = nonManufacture_isChecked;
        this.date_created = date_created;
        this.id_type_role = id_type_role;
        this.id_status = id_status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public int getNonManufacture_isChecked() {
        return nonManufacture_isChecked;
    }

    public void setNonManufacture_isChecked(int nonManufacture_isChecked) {
        this.nonManufacture_isChecked = nonManufacture_isChecked;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public long getId_type_role() {
        return id_type_role;
    }

    public void setId_type_role(long id_type_role) {
        this.id_type_role = id_type_role;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }
}