package com.my.zakupki.Net;

import java.util.List;

/**
 * Created by Dariya on 19.02.2017.
 * Base parsing rule, rules to be arranged in a tree
 */

public abstract class BaseParsingRule {
    private int FIndex;
    public BaseParsingRule(int aMyIndex)
    { FIndex = aMyIndex; }
    public abstract List<String> Apply(String aText);
    int getIndex()
    { return FIndex; }
}
