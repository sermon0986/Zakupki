package com.my.zakupki.Net;

/**
 * Created by Пользователь on 02.03.2017.
 */

public class Fz44PrintFormUrlBuilder extends BaseUrlstringBuilder {
    String FPrintUrl;

    public Fz44PrintFormUrlBuilder InitUrl(String urlPrintForm){
        FPrintUrl = "http://zakupki.gov.ru"+urlPrintForm;
        return this;
    }
    @Override
    public String Build() {
        return FPrintUrl;
    }
}
