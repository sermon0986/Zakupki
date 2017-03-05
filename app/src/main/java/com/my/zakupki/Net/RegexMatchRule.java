package com.my.zakupki.Net;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Пользователь on 22.02.2017.
 */

public class RegexMatchRule extends BaseParsingRule {
    private String FRegex;

    public RegexMatchRule(int aRegexId, String aRegex){
        super(aRegexId);
        FRegex = aRegex;
    }

    public List<String> Apply(String aText){
        List<String> result = new ArrayList<String>();
        Pattern p = Pattern.compile(FRegex, Pattern.DOTALL | Pattern.MULTILINE);
        Matcher m = p.matcher(aText);
        while(m.find()) // for all results:
            for(int i=1; i<=m.groupCount(); i++) // add all groups:
                result.add(m.group(i));
        return result;
    }
}
