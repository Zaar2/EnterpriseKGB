package com.zaar.meatkgb2_m.data;

import androidx.room.ColumnInfo;

public class NameWorker {
    @ColumnInfo(name = "full_name")
    public String fullName;
    @ColumnInfo(name = "short_name")
    public String shortName;
}
