package com.zaar.meatkgb2_m.data;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

public class AppUser {
    @Ignore
    @ColumnInfo(name = "enterpriseName")
    public String enterpriseName;
    @ColumnInfo(name = "appointment")
    public String appointment;
    @ColumnInfo(name = "full_name")
    public String usrFullName;
    @Ignore
    public String usrLogin;
}
