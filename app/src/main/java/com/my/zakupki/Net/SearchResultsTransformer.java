package com.my.zakupki.Net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dariya on 26.02.2017.
 */

public class SearchResultsTransformer extends BaseTreeResultTransformer {

//    SearchResultsTransformer(RuleTreeProvider aProvider){
//        super(aProvider.LoadRuleTree("Set2"));
//    }
    SearchResultsTransformer(TreeNode<BaseParsingRule> aRuleTree){
        super(aRuleTree);
    }

    @Override
    public Map<String, Object> Transform() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total",getResult().N().S(0,0).S(1,0).S(10,0).R());
        result.put("pagenum",getResult().N().S(0,0).S(1,0).S(11,0).R());
        result.put("pagestotal",getResult().N().S(0,0).S(1,0).S(12,0).R());
        result.put("count",Integer.valueOf(getResult().N().S(0,0).Count(2)));
        List<Map<String,String>> list = new ArrayList<>();
        result.put("expenseList", list);
        for(int i=0; i<getResult().N().S(0,0).Count(2); i++ ) {
            Map<String,String> map3 = new HashMap<>();
            map3.put("expenseType", getResult().N().S(0, 0).S(2, i).S(3,0).R());
            map3.put("expenseStage", getResult().N().S(0, 0).S(2, i).S(4,0).R());
            map3.put("expenseLaw", getResult().N().S(0, 0).S(2, i).S(5,0).R());
            map3.put("expensePrice", getResult().N().S(0, 0).S(2, i).S(6,0).R()+getResult().N().S(0, 0).S(2, i).S(6,1).R());
            map3.put("num", getResult().N().S(0, 0).S(2, i).S(7,0).R().trim());
            map3.put("organization", getResult().N().S(0, 0).S(2, i).S(7,1).R().trim());
            map3.put("subject", getResult().N().S(0, 0).S(2, i).S(7,2).R().trim());
            map3.put("urlPrintForm", getResult().N().S(0, 0).S(2, i).S(8,0).R().trim());
            map3.put("urlCommonInfo", getResult().N().S(0, 0).S(2, i).S(9,0).R().trim());
            map3.put("urlDocuments", getResult().N().S(0, 0).S(2, i).S(10,0).R().trim());
            list.add(map3);
        }
        return result;
    }

}
