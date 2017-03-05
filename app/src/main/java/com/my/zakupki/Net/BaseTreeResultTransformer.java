package com.my.zakupki.Net;

import java.util.Map;

/**
 * Created by Dariya on 24.02.2017.
 */

public abstract class BaseTreeResultTransformer {

    private TreeNode<BaseParsingRule> FRuleTree;
    private RuleTreeResultsBuilder  FResult;
    private String FSourceData="";

    BaseTreeResultTransformer(TreeNode<BaseParsingRule> aRuleTree){
        FRuleTree = aRuleTree;
//        FResult = new RuleTreeResultsBuilder(aRuleTree, aData);
    }
//    BaseTreeResultTransformer(String aData, TreeNode<BaseParsingRule> aRuleTree){
//        FResult = new RuleTreeResultsBuilder(aRuleTree, aData);
//    }
    BaseTreeResultTransformer setSourceData(String aData){
        FSourceData = aData;
        return this;
    }
    RuleTreeResultsBuilder getResult(){
        if(null == FResult)
            FResult = new RuleTreeResultsBuilder(FRuleTree, FSourceData);
        return FResult;
    }
    public abstract Map<String, Object> Transform();
}
