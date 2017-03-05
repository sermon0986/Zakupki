package com.my.zakupki.Net;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dariya on 24.02.2017.
 */

public class RuleTreeResultsBuilder {

    TreeNode<Object> FTree = null;

    protected TreeNode<Object> FBranch;
//    protected Boolean FLastRequestWasSuccessfull = false;

    RuleTreeResultsBuilder(TreeNode<BaseParsingRule> aRuleRoot, String aData){
        ApplyRuleTree(aRuleRoot, aData);
    }

    public
    TreeNode<Object> ApplyRuleTree(TreeNode<BaseParsingRule> aRuleRoot, String aData){
        FTree = new TreeNode<Object>(new String(aData));
        return RuleTreeResultsSupport.ApplyRuleTree(aRuleRoot,FTree);
    }

    /**
     * N stands for New result session
     * @return
     */
    RuleTreeResultsBuilder N(){
        FBranch = FTree;
        return this;
    }

    /**
     * S stands for Select from the current node
     * @param aRuleInd
     * @param aResultInd
     * @return
     */
    RuleTreeResultsBuilder S(int aRuleInd, int aResultInd){
        FBranch = RuleTreeResultsSupport.SelectARuleResult(FBranch,aRuleInd,aResultInd,null);
        return this;
    }

    /**
     * R stands for get Result from the current node
     * @return
     */
    String R(){
        if(null != FBranch)
            return (String)(FBranch.data);
        else return new String("");
    }

    /**
     * Count stands for get results Count for the current node
     * @param aRuleInd
     * @return
     */
    int Count(int aRuleInd){
        if(null != FBranch)
            return RuleTreeResultsSupport.GetRuleResultsCount(FBranch, aRuleInd);
        return 0;
    }

    /**
     * All stands for get All result strings for the current node
     * @param aRuleInd
     * @return
     */
    List<String> All(int aRuleInd){
        if(null != FBranch){
            return RuleTreeResultsSupport.GetAllRuleResults(FBranch,aRuleInd);
        }
        return new ArrayList<String>();
    }

}
