package com.my.zakupki.Net;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by Dariya on 20.04.2017.
 */

public class UnescapeHtmlRule extends BaseParsingRule {
    public UnescapeHtmlRule(int aMyIndex) {
        super(aMyIndex);
    }

    @Override
    public List<String> Apply(String aText) {
        List<String> result = new ArrayList<String>();
        result.add(StringEscapeUtils.unescapeHtml4(aText));
        return result;
    }
}
