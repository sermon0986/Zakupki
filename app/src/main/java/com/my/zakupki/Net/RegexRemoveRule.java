package com.my.zakupki.Net;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dariya on 20.04.2017.
 */

public class RegexRemoveRule extends BaseParsingRule {
    private String FRegex;

    public RegexRemoveRule(int aRegexId, String aRegex){
        super(aRegexId);
        FRegex = aRegex;
    }

    @Override
    public List<String> Apply(String aText) {
        List<String> result = new ArrayList<String>();
        result.add(aText.replaceAll(FRegex,""));
        return result;
    }
}
