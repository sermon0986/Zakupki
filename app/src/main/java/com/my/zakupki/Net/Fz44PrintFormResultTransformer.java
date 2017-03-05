package com.my.zakupki.Net;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Пользователь on 02.03.2017.
 */

public class Fz44PrintFormResultTransformer extends BaseTreeResultTransformer {
    public Fz44PrintFormResultTransformer(TreeNode<BaseParsingRule> aRuleTree) {
        super(aRuleTree);
    }

    @Override
    public Map<String, Object> Transform() {
        Map<String, Object> result = new HashMap<String, Object>();
        String s1 = getResult().N().S(0,0).S(1,0).R();
        String s2 = getResult().N().S(0,0).S(1,1).R();
        result.put("titleSubtitle",s1+" "+s2);
        Integer pgCount = new Integer(getResult().N().S(0,0).Count(2));
        result.put("paramgroupsCount",pgCount);
        List<Map<String,Object>> paramgroups = new ArrayList<>();
        result.put("paramgroups",paramgroups);
        for(int i=0; i<pgCount; i++){
            boolean tableIncluded = false;
            Map<String,Object> map3 = new HashMap<>();
            map3.put("groupName", getResult().N().S(0,0).S(2,i).S(7,0).R());
            map3.put("hasTable",new Integer(getResult().N().S(0,0).S(2,i).Count(3)).toString());
            List<Pair<String,String>> keyValues = new ArrayList<>();
            map3.put("keyValues",keyValues);
            // thru results for 4th rule:
            for(int j=0;j<getResult().N().S(0,0).S(2,i).Count(4); j++) {
                if (getResult().N().S(0, 0).S(2, i).S(4, j).Count(5) == 2) {
                    String key, val;
                    key = getResult().N().S(0, 0).S(2, i).S(4, j).S(5, 0).Count(6) == 0
                            ? getResult().N().S(0, 0).S(2, i).S(4, j).S(5, 0).R()
                            : getResult().N().S(0, 0).S(2, i).S(4, j).S(5, 0).S(6, 1).R() ;
                    if (getResult().N().S(0, 0).S(2, i).S(4, j).S(5, 1).Count(6) == 0)
                        val = getResult().N().S(0, 0).S(2, i).S(4, j).S(5, 1).R();
                    else {
                        StringBuilder valSb = new StringBuilder();
                        for (int k = 0; k < getResult().N().S(0, 0).S(2, i).S(4, j).S(5, 1).Count(6); k+=3) {
                            if (k != 0) valSb.append("\n");
                            valSb.append(getResult().N().S(0, 0).S(2, i).S(4, j).S(5, 1).S(6, k+1).R());
                        }
                        val = valSb.toString();
                    }
                    keyValues.add(new Pair<String, String>(key, val));
                }
                else if(getResult().N().S(0, 0).S(2, i).S(4, j).Count(5) > 2 && getResult().N().S(0,0).S(2,i).Count(3) > 0 && !tableIncluded){
                    // seems to be the table beginning       and has table      and no table included in the result yet:
                    keyValues.add(new Pair<String, String>("table",getResult().N().S(0,0).S(2,i).S(3,0).R()));
                    tableIncluded = true;
                }
            }
            paramgroups.add(map3);
        }

        return result;
    }
}
