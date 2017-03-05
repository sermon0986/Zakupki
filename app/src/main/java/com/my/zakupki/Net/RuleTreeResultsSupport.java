package com.my.zakupki.Net;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dariya on 24.02.2017.
 */

public class RuleTreeResultsSupport {
    /**
     * Returns a new tree node holding a String element to be a root for the string result.
     * @param aData is a String for which a rule tree will be applied
     * @return      an element to be passed to ApplyRuleTree as aDataRoot
     * @see         RuleTreeResultsSupport#ApplyRuleTree(TreeNode, TreeNode)
     */
    public static
    TreeNode<Object> MakeDataRoot(String aData) {
        return new TreeNode<Object>(aData);
    }

    // Regex indexed are expected to be placed in a tree:
    // for a tree of
    //     ____RI0____        This is aRuleRoot
    //     |    |    |
    //   RI1   RI2   RI3       These are childRule's which are aRuleRoot.children
    //
    //  result would be:
    //             Src Data     this is a String of a data referenced by aDataRoot
    //                |
    //    ___________RI0________________     this is an Index node of type Integer referenced by aRuleRoot
    //   |                 |            |
    //  String A          String B     String C      These are selfResult referenced by "child" of a ruleRoot (results for application RI0 regex to the string Src data)
    //  |   |   |         |   |   |       etc.
    // RI1 RI2  RI3      RI1 RI2  RI3                    These are next rules results referenced by "childRoot" and placed into ruleRoot children
    //  |   |   |  |      etc.
    // S1  S2   S31 S32    etc.

    /**
     * Builds a result tree, starting from a given aDataRoot node, applying a rule tree
     * to it's string content.
     * @param aRuleRoot is a rule tree root
     * @param aDataRoot is a node holding a source string data
     * @return          a tree-organized result, each string result node is followed by children
     *                  nodes which hold Integer representing a rule index. Each Integer node
     *                  is followed by the regexp string results node set.
     * @see             BaseParsingRule
     */

    public static
    TreeNode<Object> ApplyRuleTree(TreeNode<BaseParsingRule> aRuleRoot, TreeNode<Object> aDataRoot){
        Log.d("RuleTreeResultsSupport","Apply Rule No"+aRuleRoot.data.getIndex()+" to string "+aDataRoot.data);
        TreeNode<Object> ruleRoot = aDataRoot.addChild(Integer.valueOf(aRuleRoot.data.getIndex()));
        // apply self:
        List<String> selfResult = aRuleRoot.data.Apply((String)aDataRoot.data);
        Log.d("RuleTreeResultsSupport","Got results: "+selfResult.size());
        for(String oneSelfRes : selfResult){
            Log.d("RuleTreeResultsSupport"," : "+oneSelfRes);
        }
        Log.d("RuleTreeResultsSupport","Now processing child rules on the results:");
        // add the next result for self:
        for(String oneSelfRes : selfResult){
            if(null == oneSelfRes)
                continue;
            TreeNode<Object> child = ruleRoot.addChild(oneSelfRes);
            // go deeper:
            // for the child add as a child branches of the rule's children:
            for(TreeNode<BaseParsingRule> childRule : aRuleRoot.children) {
                Log.d("RuleTreeResultsSupport","result : "+oneSelfRes+" rule id: "+childRule.data.getIndex());
                // apply the child regex:
                TreeNode<Object> childRoot = ApplyRuleTree(childRule, child);
                // add results:
                child.addChild(childRoot);
            }
        }
        return ruleRoot;
    }

    /**
     * Returns number of children in the result node for a specified rule index.
     * If no such rule has been applied to the node, returns 0.
     * @param aResultRoot   a result node
     * @param aRuleInd      an index corresponding to an indexed rule in an applied rule tree
     * @return              number of results
     * @see                 BaseParsingRule
     */
    public static
    int GetRuleResultsCount(TreeNode<Object> aResultRoot, int aRuleInd){
        if(aResultRoot.children == null)
            return 0;
        for(TreeNode<Object> child : aResultRoot.children)
            if(child.data instanceof Integer) // short-circuiting logical AND and unboxing
                if((Integer)(child.data) == aRuleInd)
                    return child.children.size();
        return 0;
    }

    /**
     * Returns all the String results for a specific source node, produced by the indexed rule
     * @param aResultRoot   a result root
     * @param aRuleInd      an index for a rule
     * @return              a list of strings
     * @see                 BaseParsingRule
     */
    public static
    List<String> GetAllRuleResults(TreeNode<Object> aResultRoot, int aRuleInd) {
        List<String> results = new ArrayList();
        if(aResultRoot.children == null) {
            return results;
        }
        for(TreeNode<Object> child : aResultRoot.children)
            if(child.data instanceof Integer) // short-circuiting logical AND and unboxing
                if((Integer)(child.data) == aRuleInd) {
                    for (TreeNode<Object> strResult : child.children)
                        results.add((String)strResult.data);
                    break;
                }
        return results;
    }

    /**
     * Returns a list of nodes, holding results for the indexed rule, applied to the given source node
     * @param aResultRoot   a result node
     * @param aRuleInd      a rule index, whose results are requested
     * @return              a list of result nodes
     */
    public static
    List<TreeNode<Object>> GetAllRuleResultsTrees(TreeNode<Object> aResultRoot, int aRuleInd) {
        List<TreeNode<Object>> resultsTrees = new ArrayList();
        if(aResultRoot.children == null) {
            return resultsTrees;
        }
        for(TreeNode<Object> child : aResultRoot.children)
            if(child.data instanceof Integer) // short-circuiting logical AND and unboxing
                if((Integer)(child.data) == aRuleInd)
                    resultsTrees = child.children;
        return resultsTrees;
    }

    // Selects in the rule branch of rule index aRuleInd (if the branch exists)
    // it's result under index aResultInd (if such a result is present),
    // and place it into aRes (otherwise aRes are given a null value),
    // and return in aResultRoot its result tree, if any exits (otherwise NULL)
    public static
    TreeNode<Object> SelectARuleResult(TreeNode<Object> aResultRoot, int aRuleInd, int aResultInd, StringBuilder aRes){
        if(null == aResultRoot || (null != aResultRoot && aResultRoot.children == null)) {
            aRes = null; // in case a property that may cause an unexpected launch
            return null;
        }
        for(TreeNode<Object> child : aResultRoot.children)
            if(child.data instanceof Integer) // short-circuiting logical AND and unboxing
                if((Integer)(child.data) == aRuleInd)
                    if(!child.children.isEmpty() && child.children.size() > aResultInd){
                        if(aRes != null)
                            aRes.append((String)child.children.get(aResultInd).data); // that is String
                        // that is a tree :
                        return child.children.get(aResultInd);
                    }
        aRes = null; // in case a property that may cause an unexpected launch
        return null;
    }
}
