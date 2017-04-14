package com.my.zakupki.Net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dariya on 15.04.2017.
 */

public class Fz223DocsTransformer extends BaseTreeResultTransformer {
    public Fz223DocsTransformer(TreeNode<BaseParsingRule> aRuleTree) {
        super(aRuleTree);
    }

    @Override
    public Map<String, Object> Transform() {
        Map<String, Object> result = new HashMap<String, Object>();
        Integer pgCount = new Integer(getResult().N().Count(0));
        result.put("paramgroupsCount",pgCount);
        List<Map<String,Object>> paramgroups = new ArrayList<>();
        result.put("paramgroups",paramgroups);
        for(int i=0; i<pgCount; i++) {
            Map<String,Object> map2 = new HashMap<>();
            map2.put("paramgroupName",getResult().N().S(0,i).S(1,0).R());
            List<Map<String,String>> filesList = new ArrayList<>();
            map2.put("filesList",filesList);
            Integer filesCount = getResult().N().S(0,i).S(1,1).Count(2);
            for(int fileNum=0; fileNum<filesCount; fileNum++){
                Map<String,String> map3 = new HashMap<>();
                map3.put("url", getResult().N().S(0,i).S(1,1).S(2,fileNum).S(3,0).R());
                map3.put("title", getResult().N().S(0,i).S(1,1).S(2,fileNum).S(3,1).R());
                map3.put("fileName", getResult().N().S(0,i).S(1,1).S(2,fileNum).S(3,2).R());
                filesList.add(map3);
            }
            map2.put("pagesTotal",getResult().N().S(0,i).S(1,1).S(4,0).R());
            map2.put("pageNum",getResult().N().S(0,i).S(1,1).S(5,0).R());
            map2.put("nextPageUrl",getResult().N().S(0,i).S(1,1).S(6,0).R());
            paramgroups.add(map2);
        }
        return result;
    }
}
