package com.my.zakupki;

import com.my.zakupki.DataClasses.Deal;
import com.my.zakupki.DataClasses.DealList;
import com.my.zakupki.Net.ExpenseSearchUrlBuilder;
import com.my.zakupki.Utils.NumberUtils;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;

public class Common {

    public static DealList Favorites;
    public static Deal CurrentDeal;

    public static boolean NeedRefreshMain;

    public static List<Deal> DealListResults;

    public static int ResultsTotal;
    public static int PagesTotal;
    public static int PagesNum;
    public static int ResultsCount;


    public static OkHttpClient maClient;
    public static ExpenseSearchUrlBuilder FSearchUrlBuilder;

    public static void ParseResults(Map<String, Object> resSet2)
    {
        Common.ResultsTotal = NumberUtils.parseIntSafely(resSet2.get("total").toString().replace(" ", ""));
        Common.PagesNum = NumberUtils.parseIntSafely(resSet2.get("pagenum").toString().replace(" ", ""));
        Common.PagesTotal = NumberUtils.parseIntSafely(resSet2.get("pagestotal").toString().replace(" ", ""));
        Common.ResultsCount = NumberUtils.parseIntSafely(resSet2.get("count").toString().replace(" ", ""));

        List<Map<String,String>> list = (List<Map<String,String>>)resSet2.get("expenseList");
        for(Map<String,String> map3 : list){
            Deal new_deal=new Deal();
            new_deal.Number=map3.get("num").toString();
            new_deal.PublishType=map3.get("expenseType").toString();
            new_deal.Publisher=map3.get("organization").toString();
            new_deal.CurrentStatus=map3.get("expenseStage").toString();
            new_deal.Price=map3.get("expensePrice").toString();
            new_deal.Description=map3.get("subject").toString();
            new_deal.UrlPrintForm=map3.get("urlPrintForm").toString();
            new_deal.UrlCommonInfo=map3.get("urlCommonInfo").toString();
            new_deal.UrlDocuments=map3.get("urlDocuments").toString();
            Common.DealListResults.add(new_deal);
        }
    }
}
