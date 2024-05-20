package com.zaar.meatkgb2_m.utilities;

public interface Const {
    String NAME_DATABASE = "db_meatKGBm.db";
    int CRYPTO_STR_LENGTH = 10;
    String APP_PREFERENCES_NAME = "meatKGB_enterpriseCryptoID";
    /**
     * HTTP status code - session has expired or not exists
     */
    int HTTP_ERR_SESSION_EXPIRED_EXISTS = 236;
    int HTTP_STATUS_ZERO_AFFECTED_ROWS = 237;
    int HTTP_ERR_RESPONSE_SERV_NO_CONTENT = 204;
    int HTTP_ERR_RESPONSE_SERV_NOT_FOUND = 238;
    int HTTP_ERR_RESPONSE_SERV_HEADER_TEXT = 201;
    int HTTP_ERR_RESPONSE_SERV_ERROR_DB = 239;


}