package com.my.zakupki.DataClasses;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ParamGroupList {

    public List<ParamGroup> Items;

    public ParamGroupList()
    {
        Items=new ArrayList<>();
    }

    public String ToJSON()
    {
        JSONArray array=new JSONArray();
        try {
            for(ParamGroup paramGroup:Items)
                array.put(paramGroup.ToJSON());
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
                ParamGroup paramGroup=new ParamGroup();
                paramGroup.FromJSON(array.getJSONObject(i));
                Items.add(paramGroup);
            }
        } catch (Exception ignore) {
        }
    }
}
