package com.my.zakupki.Net;

import java.util.Map;

/**
 * Created by Пользователь on 27.02.2017.
 */

public interface PageLoaderCallbackInterface {
    void onSuccess(Map<String, Object> resultSet);
    void onFail(String err);
    void onTimeOut();
}
