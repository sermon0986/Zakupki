package com.my.zakupki.Net;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dariya on 18.04.2017.
 */

public class UniZhournalUrlBuilder extends BaseUrlstringBuilder {

    String FZhournalUrl;

    public UniZhournalUrlBuilder InitUrl(String urlCommonInfo){
        final String fz223prefix = "http://zakupki.gov.ru/223/purchase/public/purchase/info/journal.html?";
        final String fz44prefix  = "http://zakupki.gov.ru/epz/order/notice/ep44/view/event-journal.html?";
        Pattern p = Pattern.compile("\\?(.*)", Pattern.DOTALL | Pattern.MULTILINE);
        Matcher m = p.matcher(urlCommonInfo);
        String expenseId = new String("");
        if(m.find() && 1<=m.groupCount())
            expenseId = m.group(1);
        if(expenseId.startsWith("regNumber")) // fz 44
            FZhournalUrl = fz44prefix + expenseId;
        else if(expenseId.startsWith("noticeId")) // fz 223
            FZhournalUrl = fz223prefix + expenseId;
        return this;
    }

    @Override
    public String Build() {
        return FZhournalUrl;
    }
}
