package com.zaar.meatkgb2_m.model.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Workers"
//        foreignKeys = @ForeignKey(
//                entity = Enterprise.class,
//                parentColumns = "cryptoId",
//                childColumns = "enterprise_cryptoId",
//                onDelete = ForeignKey.CASCADE,
//                onUpdate = ForeignKey.CASCADE
//        )
)
public class User {
    @PrimaryKey(autoGenerate = true)
    protected long id;
    protected String full_name;
    protected String short_name;
    protected String usr_login;
    protected long id_workshop;
    protected String appointment;
    protected String usr_pass;
    protected String enterpriseId;
    protected int id_status;
    protected long id_one_more_workshop;
    @Ignore
    protected String login_user_created;


    public User(
            String full_name,
            String short_name,
            String usr_login,
            long id_workshop,
            String appointment,
            String usr_pass,
            String enterpriseId,
            int id_status,
            long id_one_more_workshop
    ) {
        this.full_name = full_name;
        this.short_name = short_name;
        this.usr_login = usr_login;
        this.id_workshop = id_workshop;
        this.appointment = appointment;
        this.usr_pass = usr_pass;
        this.enterpriseId = enterpriseId;
        this.id_status = id_status;
        this.id_one_more_workshop = id_one_more_workshop;
        this.login_user_created = "";
    }

    @Ignore
    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getUsr_login() {
        return usr_login;
    }

    public void setUsr_login(String usr_login) {
        this.usr_login = usr_login;
    }

    public long getId_workshop() {
        return id_workshop;
    }

    public void setId_workshop(long id_workshop) {
        this.id_workshop = id_workshop;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getUsr_pass() {
        return usr_pass;
    }

    public void setUsr_pass(String usr_pass) {
        this.usr_pass = usr_pass;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public long getId_one_more_workshop() {
        return id_one_more_workshop;
    }

    public void setId_one_more_workshop(long id_one_more_workshop) {
        this.id_one_more_workshop = id_one_more_workshop;
    }

    public String getLogin_user_created() {
        return login_user_created;
    }

    public void setLogin_user_created(String login_user_created) {
        this.login_user_created = login_user_created;
    }
}