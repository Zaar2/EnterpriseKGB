package com.zaar.meatkgb2_m.model.local;

public class EnabledAppUser {
    private static EnabledAppUser INSTANCE = null;

    private String
            enterpriseName,
            appointment,
            usrFullName,
            usrLogin,
            sessionID;
    private boolean isInit;

    private EnabledAppUser() {
        enterpriseName = "";
        appointment = "";
        usrFullName = "";
        isInit = false;
        sessionID = "";
        usrLogin = "";
    }

    public static synchronized EnabledAppUser getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new EnabledAppUser();
            INSTANCE.setInit(true);
        }
        return INSTANCE;
    }

    public void reset() {
        enterpriseName = null;
        appointment = null;
        usrFullName = null;
        usrLogin = null;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getUsrFullName() {
        return usrFullName;
    }

    public void setUsrFullName(String usrFullName) {
        this.usrFullName = usrFullName;
    }

    public boolean isInit() {
        return isInit;
    }

    public void setInit(boolean init) {
        isInit = init;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getUsrLogin() {
        return usrLogin;
    }

    public void setUsrLogin(String usrLogin) {
        this.usrLogin = usrLogin;
    }
}