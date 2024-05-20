package com.zaar.meatkgb2_m.data;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.Insert;

public class Workers_shortDescription {
    @ColumnInfo(name = "id")
    public long id;
    @ColumnInfo(name = "full_name")
    public String fullName;
    @ColumnInfo(name = "id_workshop")
    public int id_workshop;
    @ColumnInfo(name = "appointment")
    public String appointment;
    @Ignore
    public String nameWorkshop;
}
