package com.zaar.meatkgb2_m.data;

import androidx.room.ColumnInfo;

public class LogPass {
    @ColumnInfo(name = "enterpriseId")
    public String enterpriseId;
    @ColumnInfo(name = "usr_login")
    public String usrLogin;
    @ColumnInfo(name = "usr_pass")
    public String usrPass;

    public LogPass() {
    }

    public LogPass(String enterpriseId, String usrLogin, String usrPass) {
        this.enterpriseId = enterpriseId;
        this.usrLogin = usrLogin;
        this.usrPass = usrPass;
    }

    public boolean isEmpty() {
        return enterpriseId.isEmpty()
                || usrPass.isEmpty()
                || usrLogin.isEmpty();
    }
}