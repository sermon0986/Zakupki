package com.my.zakupki.DataClasses;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ParamList {

    public List<Param> Items;

    public ParamList()
    {
        Items=new ArrayList<>();
    }

    public String ToJSON()
    {
        JSONArray array=new JSONArray();
        try {
            for(Param param:Items)
                array.put(param.ToJSON());
        }
        catch (Exception ignore)
        {}
        return array.toString();
    }

    public void FromJSON(String str) {
        Items.clear();
        try {
            JSONArray array=new JSONArray(str);
            int num=array.length();
            for(int i=0;i<num;i++){
                Param param=new Param();
                param.FromJSON(array.getJSONObject(i));
                Items.add(param);
            }
        } catch (Exception ignore) {
        }
    }
}
