package com.my.zakupki.Net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Dariya on 18.04.2017.
 */

public class UniZhournalTransformer extends BaseTreeResultTransformer {
    UniZhournalTransformer(TreeNode<BaseParsingRule> aRuleTree){
        super(aRuleTree);
    }
    @Override
    public Map<String, Object> Transform() {
        Map<String, Object> result = new HashMap<String, Object>();
        Integer eventsCount = new Integer(getResult().N().S(0,0).S(4,0).R());
        result.put("eventsCount",eventsCount);
        List<Map<String,String>> events = new ArrayList<>();
        result.put("events",events);
        for(int i=0; i<eventsCount; i++){
            Map<String,String> oneEvent = new HashMap<>();
            oneEvent.put("dateTime",  getResult().N().S(0,0).S(2,i).S(3,0).R());
            oneEvent.put("eventDescr",getResult().N().S(0,0).S(2,i).S(3,1).R());
            events.add(oneEvent);
        }
        return result;
    }
}
