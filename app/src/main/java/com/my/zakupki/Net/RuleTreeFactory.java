package com.my.zakupki.Net;

/**
 * Created by Пользователь on 28.02.2017.
 */

public enum RuleTreeFactory  {
    INSTANCE;

    private RuleTreeFactory(){}

    public BaseTreeResultTransformer getTransformer(final String aTag){
        if("SearchResults".equals(aTag))
            return new SearchResultsTransformer(LoadRuleTree(aTag));
        if("Fz44_Test_1".equals(aTag))
            return new Fz44PrintFormResultTransformer(LoadRuleTree(aTag));
        if("Fz223_Test_1".equals(aTag))
            return new Fz223PrintFormResultTransformer(LoadRuleTree(aTag));
        else
            return new Set1ResultTransformer(LoadRuleTree("Set1"));
    }

    //    @Override
    public TreeNode<BaseParsingRule> LoadRuleTree(final String aTag){
        if("Set1".equals(aTag))
            return getSet1();
        if("SearchResults".equals(aTag))
            return getSearchResultsTree();
        if("Fz44_Test_1".equals(aTag))
            return getFz44RuleTree();
        if("Fz223_Test_1".equals(aTag))
            return getFz223RuleTree();
        if("Fz44_Docs".equals(aTag))
            return getFz44DocsRuleTree();
        if("Fz223_Docs".equals(aTag))
            return getFz223DocsRuleTree();
        return new TreeNode<BaseParsingRule>(new RegexFindRule(0,"<html>.*</html>"));
    }

    public TreeNode<BaseParsingRule>  getSet1() {
        TreeNode<BaseParsingRule> root0 = new TreeNode<BaseParsingRule>(new RegexFindRule(0,"<html>.*</html>"));
        TreeNode<BaseParsingRule> root1 = root0.addChild(new RegexMatchRule(1,"<p\\sclass=\"allRecords\">.*?<strong>(.*?)</strong>.*?</p>"));
//        TreeNode<IndexedRegex> root = new TreeNode<IndexedRegex>(new IndexedRegex(0,"<(?:\"[^\"]*\"['\"]*|'[^']*'['\"]*|[^'\">])+>"));
        TreeNode<BaseParsingRule> next2 = root0.addChild(new RegexFindRule(2,"<div class=\"registerBox registerBoxBank margBtm20\">.*?(?=<div class=\"registerBox registerBoxBank margBtm20\">|<div class=\"margBtm50\">)"));
        TreeNode<BaseParsingRule> next3 = next2.addChild(new RegexMatchRule(3,"<a[^>]*?(?=href)href=\"(.*?)\""));
        return root0;
    }

    public TreeNode<BaseParsingRule>  getSearchResultsTree() {
        TreeNode<BaseParsingRule> root0 = new TreeNode<BaseParsingRule>(new RegexFindRule(0,"<html>.*</html>"));
        TreeNode<BaseParsingRule> next1 = root0.addChild(new RegexMatchRule(1,"<div class=\"paginator greyBox\">(.*?)(?=<div class=\"registerBox registerBoxBank margBtm20\">)"));
        TreeNode<BaseParsingRule> next11 = next1.addChild(new RegexMatchRule(10,"<p\\sclass=\"allRecords\">.*?<strong>(.*?)</strong>.*?</p>"));
        TreeNode<BaseParsingRule> next12 = next1.addChild(new RegexMatchRule(11,"<li class=\"currentPage\">(.*?)</li>"));
        TreeNode<BaseParsingRule> next13 = next1.addChild(new RegexMatchRule(12,"<li class=\"rightArrow\">[^0-9]*(\\d+)"));

        TreeNode<BaseParsingRule> next2 = root0.addChild(new RegexFindRule(2,"<div class=\"registerBox registerBoxBank margBtm20\">.*?(?=<div class=\"registerBox registerBoxBank margBtm20\">|<div class=\"margBtm50\">)"));
        // тип закупки:
        TreeNode<BaseParsingRule> next21 = next2.addChild(new RegexMatchRule(3,"<strong>\\s*([\\w\\s:(),&#\\d;]+)\\s*</strong>"));
        // статус закупки:
        TreeNode<BaseParsingRule> next22 = next2.addChild(new RegexMatchRule(4,"<span class=\"checked noWrap\">(.*?)\\s/\\s<span"));
        // закон:
        TreeNode<BaseParsingRule> next23 = next2.addChild(new RegexMatchRule(5,"class=\"orange\">(.*?)</span></span>"));
        // денег:
        TreeNode<BaseParsingRule> next24 = next2.addChild(new RegexMatchRule(6,"<strong>\\s*([\\d\\p{Blank}]+)\\s*<span>(.*?)</span>"));

//        TreeNode<BaseParsingRule> next22 = next2.addChild(new RegexMatchRule(3,"<td class=\"tenderTd\">[A-Za-z<>/\\s]+<strong>\\s*([\\w[:blank:]:(),]+)\\s*</strong>.*?<span class=\"[\\w\\s]+\">([\\w\\x20:(),]+)/.*?<strong>\\s*([\\d[:blank:]]+)\\s*<span>"));
        TreeNode<BaseParsingRule> next25 = next2.addChild(new RegexMatchRule(7,"<td class=\"descriptTenderTd\">.*(№\\s\\d+).*Заказчик:\\s*<ul>\\s*<li>\\s*<a.*?>([-\\w\\s\",.()№:A-Za-z=<>/]+)</a>.*?<dd>(.*?)</dd>.*?</td>"));
        TreeNode<BaseParsingRule> next26 = next2.addChild(new RegexMatchRule(8,"<a[^>]*?(?=href)href=\"(.*?/[Pp]rint-?[Ff]orm/.*?)\""));
        TreeNode<BaseParsingRule> next27 = next2.addChild(new RegexMatchRule(9,"<a[^>]*?(?=href)href=\"([^\"]*?common-info.*?)\""));
        TreeNode<BaseParsingRule> next28 = next2.addChild(new RegexMatchRule(10,"<a[^>]*?(?=href)href=\"([^\"]*?documents.*?)\""));
        return root0;
    }

    public TreeNode<BaseParsingRule> getFz44RuleTree(){
        TreeNode<BaseParsingRule> root0 = new TreeNode<BaseParsingRule>(new RegexFindRule(0,"<body>.*</body>"));
        TreeNode<BaseParsingRule> next1 = root0.addChild(new RegexMatchRule(1,"<p\\s+class=\"title\">(.*)</p>\\s*<p\\s+class=\"subtitle\">(.*?)</p>"));
        TreeNode<BaseParsingRule> next2 = root0.addChild(new RegexFindRule(2,"<tr\\s*[^>]*?>\\s*<td>\\s*<p\\s+class=\"caption\">.*?</p>.*?(?=<tr\\s*[^>]*?>\\s*<td>\\s*<p\\s+class=\"caption\">.*?</p>|</body>)"));
        TreeNode<BaseParsingRule> next20 = next2.addChild(new RegexMatchRule(7,"<p\\sclass=\"caption\"><b>(.*?)</b></p>"));
        TreeNode<BaseParsingRule> next21 = next2.addChild(new RegexFindRule(3,"(<table[^>]*>.*</table>)"));
        TreeNode<BaseParsingRule> next22 = next2.addChild(new RegexFindRule(4,"<tr>.*?</tr>"));
        TreeNode<BaseParsingRule> next41 = next22.addChild(new RegexMatchRule(5,"<td[^>]*?>(.*?)</td>"));
        TreeNode<BaseParsingRule> next51 = next41.addChild(new RegexMatchRule(6,"(<p[^>]*>|<b>)(.*?)(</p>|</b>)"));
        return root0;
    }

    public TreeNode<BaseParsingRule> getFz223RuleTree(){
        TreeNode<BaseParsingRule> root0 = new TreeNode<BaseParsingRule>(new RegexFindRule(0,"<html\\s[^>]+>.*?</html>"));
        TreeNode<BaseParsingRule> next1 = root0.addChild(new RegexMatchRule(1,"\"title\">([^<]*).*\"titleBottom\">([^<]*)"));
        TreeNode<BaseParsingRule> next2 = root0.addChild(new RegexFindRule(2,"(\"titleBottom\">[^<]*</td>|\"subTitle\">([^<]*))(.*?)(?=\"subTitle\">[^<]*|</html>)"));
        TreeNode<BaseParsingRule> next20 = next2.addChild(new RegexMatchRule(7,"\"subTitle\">(.*?)<"));
        TreeNode<BaseParsingRule> next21 = next2.addChild(new RegexFindRule(3,"(<table[^>]*>.*</table>)"));
        TreeNode<BaseParsingRule> next22 = next2.addChild(new RegexFindRule(4,"<tr\\s*[^>]*?>(.*?)(?=</tr>)"));
        TreeNode<BaseParsingRule> next41 = next22.addChild(new RegexMatchRule(5,"<td[^>]*?>(.*?)</td>"));
        TreeNode<BaseParsingRule> next51 = next41.addChild(new RegexMatchRule(6,"(<p[^>]*>|<b>)(.*?)(</p>|</b>)"));
        return root0;
    }

    public TreeNode<BaseParsingRule> getFz44DocsRuleTree(){
        TreeNode<BaseParsingRule> root0 = new TreeNode<BaseParsingRule>(new RegexFindRule(0,"<h2 class=\"noticeBoxH2\">(.*?)</h2>(.*?)(?=<h2|</body>)"));
        TreeNode<BaseParsingRule> next0 = root0.addChild(new RegexMatchRule(1,"<h2 class=\"noticeBoxH2\">(.*?)</h2>(.*)"));
        TreeNode<BaseParsingRule> next1 = next0.addChild(new RegexFindRule(2,"<a\\s*href=\"[^>]+?\"\\s*title=\".*?\">\\s*.*?\\s*</a>"));
        TreeNode<BaseParsingRule> next2 = next1.addChild(new RegexMatchRule(3,"<a\\s*href=\"([^>]+?)\"\\s*title=\"(.*?)\">\\s*(.*?)\\s*</a>"));
        return root0;
    }

    public TreeNode<BaseParsingRule> getFz223DocsRuleTree(){
        TreeNode<BaseParsingRule> root0 = new TreeNode<BaseParsingRule>(new RegexFindRule(0,"<h2>.*?</h2>.*?(?=<h2|</body>)"));
        TreeNode<BaseParsingRule> next0 = root0.addChild(new RegexMatchRule(1,"<h2>(.*?)</h2>(.*)"));
        TreeNode<BaseParsingRule> next11 = next0.addChild(new RegexFindRule(2,"<a class=\"epz_aware\"\\s*href=\".*?\"\\s*title=\".*?\"\\s*onclick=\"doNotLocking\\(\\);\">.*?</a>"));
        TreeNode<BaseParsingRule> next12 = next11.addChild(new RegexMatchRule(3,"<a class=\"epz_aware\"\\s*href=\"(.*?)\"\\s*title=\"(.*?)\"\\s*onclick=\"doNotLocking\\(\\);\">(.*?)</a>"));

        TreeNode<BaseParsingRule> next2 = next0.addChild(new RegexMatchRule(4,"<span class=\"allRecords\">.*?<strong>(\\d+)</strong>"));
        TreeNode<BaseParsingRule> next3 = next0.addChild(new RegexMatchRule(5,"<li class=\"currentPage\"><span>(\\d+)</span>"));
        TreeNode<BaseParsingRule> next4 = next0.addChild(new RegexMatchRule(6,"<li class=\"rightArrow\"><a href=\"(.*?)\"></a>"));
        return root0;
    }
}

