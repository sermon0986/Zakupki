package com.my.zakupki.Net;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import android.support.v7.app.AppCompatActivity;

import com.my.zakupki.R;

import okhttp3.HttpUrl;

/**
 * Created by Пользователь on 28.02.2017.
 */

public class ExpenseSearchUrlBuilder extends BaseUrlstringBuilder{

    private AppCompatActivity FApp;
    private Map<String,String> FParams;
    private HttpUrl.Builder FUrlBuilder;
    private int FPageNum;

    ExpenseSearchUrlBuilder(){}
    public ExpenseSearchUrlBuilder setApp(AppCompatActivity app){
        FApp = app;
        return this;
    }
    public ExpenseSearchUrlBuilder InitUrl(Map<String, String> aParams){
        FParams = aParams;
        return this;
    }
    public static Map<String,String> getEmptyMap(){
        Map<String,String> map = new HashMap<>();
        map.put("morphology","on");
        map.put("pageNumber","1");
        map.put("recordsPerPage","_10");
        map.put("fz44","on");
        map.put("fz223","on");
        return map;
    }
    public ExpenseSearchUrlBuilder setPageNum(int aPageNum){
        FPageNum = aPageNum;
        return this;
    }
    int getPageNum(){
        return FPageNum;
    }

    @Override
    public String Build(){
        if(null == FUrlBuilder && null != FParams && null != FApp){
            String url = FApp.getResources().getString(R.string.ExpenseSearchUrl);
            HttpUrl okUrl = HttpUrl.parse(url);
            if(null != okUrl){
                FUrlBuilder = okUrl.newBuilder(url);
                Iterator it = FParams.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    FUrlBuilder.addQueryParameter((String)(pair.getKey()), (String)(pair.getValue()));
                }
                return FUrlBuilder.build().toString();
            }
            else{
                // TODO: url is not well-formed
                return new String("");
            }
        }
        else if(null != FUrlBuilder && null != FApp){
            String pageToken = FApp.getResources().getString(R.string.ExpenseSearchUrlTokenPageNumber);
            FUrlBuilder.removeAllQueryParameters(pageToken);
            FUrlBuilder.addQueryParameter(pageToken, ""+getPageNum());
            String x = FUrlBuilder.build().toString();
            return FUrlBuilder.build().toString();
        }
        return new String("");
    }
}
