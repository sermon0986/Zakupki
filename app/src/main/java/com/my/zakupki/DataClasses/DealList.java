package com.my.zakupki.DataClasses;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class DealList {

    public List<Deal> Items;

    public DealList()
    {
        Items=new ArrayList<>();
    }

    public String ToString()
    {
        JSONArray array=new JSONArray();
        try {
            for(Deal deal:Items)
                array.put(deal.ToJSON());
        }
        catch (Exception ignore)
        {}
        return array.toString();
    }

    public void FromString(String str) {
        Items.clear();
        try {
            JSONArray array=new JSONArray(str);
            int num=array.length();
            for(int i=0;i<num;i++){
                Deal deal=new Deal();
                deal.FromJSON(array.getJSONObject(i));
                Items.add(deal);
            }
        } catch (Exception ignore) {
        }
    }
}
