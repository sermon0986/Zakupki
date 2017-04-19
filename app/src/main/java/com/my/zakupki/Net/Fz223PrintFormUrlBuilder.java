package com.my.zakupki.Net;

/**
 * Created by Пользователь on 03.03.2017.
 */

public class Fz223PrintFormUrlBuilder extends BaseUrlstringBuilder {
    String FPrintUrl;

    public Fz223PrintFormUrlBuilder InitUrl(String urlPrintForm){
        FPrintUrl = urlPrintForm;
        return this;
    }
    @Override
    public String Build() {
        return FPrintUrl;
    }
}
