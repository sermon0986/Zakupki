package com.my.zakupki.Net;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dariya on 19.02.2017.
 */

public class RegexFindRule extends BaseParsingRule {
    private String FRegex;

    public RegexFindRule(int aRegexId, String aRegex){
        super(aRegexId);
        FRegex = aRegex;
    }

    public List<String> Apply(String aText){
        List<String> result = new ArrayList<String>();
        Pattern p = Pattern.compile(FRegex, Pattern.DOTALL | Pattern.MULTILINE);
        Matcher m = p.matcher(aText);
        while(m.find())
            // add the next result for self:
            result.add(m.group(0)); // include zero group
        return result;
    }
}
