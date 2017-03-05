package com.my.zakupki.Net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dariya on 24.02.2017.
 */

public class Set1ResultTransformer extends BaseTreeResultTransformer {

//    Set1ResultTransformer(RuleTreeProvider aProvider){
//        super(aProvider.LoadRuleTree("Set1"));
//    }

    Set1ResultTransformer(TreeNode<BaseParsingRule> aRuleTree){
        super(aRuleTree);
    }

    /*
    for a tree like:
    public TreeNode<BaseParsingRule>  getSet1() {
        TreeNode<BaseParsingRule> root0 = new TreeNode<BaseParsingRule>(new RegexFindRule(0,"<html>.*</html>"));
        TreeNode<BaseParsingRule> root1 = root0.addChild(new RegexMatchRule(1,"<p\\sclass=\"allRecords\">.*?<strong>(.*?)</strong>.*?</p>"));
        TreeNode<BaseParsingRule> next2 = root0.addChild(new RegexFindRule(2,"<div class=\"registerBox registerBoxBank margBtm20\">.*?(?=<div class=\"registerBox registerBoxBank margBtm20\">|<div class=\"margBtm50\">)"));
        TreeNode<BaseParsingRule> next3 = next2.addChild(new RegexMatchRule(3,"<a[^>]*?(?=href)href=\"(.*?)\""));
        return root0;
    }

     */
    @Override
    public Map<String, Object> Transform() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total",getResult().N().S(0,0).S(1,0).R());
        result.put("count",Integer.valueOf(getResult().N().S(0,0).Count(2)));
        List<List<String>> listList = new ArrayList<>();
        result.put("ulrList", listList);
        for(int i=0; i<getResult().N().S(0,0).Count(2); i++ ) {
            List<String> lstr3 = getResult().N().S(0, 0).S(2, i).All(3);
            listList.add(lstr3);
        }
        return result;
    }
}
