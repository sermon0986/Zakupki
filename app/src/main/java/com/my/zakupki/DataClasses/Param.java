package com.my.zakupki.DataClasses;

import org.json.JSONObject;

public class Param {
    public String Name="";
    public String Value="";

    public JSONObject ToJSON()
    {
        JSONObject object=new JSONObject();
        try {
            object.put("Name", Name);
            object.put("Value", Value);
        }
        catch (Exception ignore)
        {}
        return object;
    }

    public void FromJSON(JSONObject object) {
        try {
            Name = object.optString("Name");
            Value = object.optString("Value");
        } catch (Exception ignore) {
        }
    }
}
