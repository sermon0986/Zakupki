package com.my.zakupki.DataClasses;

import org.json.JSONObject;

public class ParamGroup {

    public String Name="";
    public ParamList Params;

    public ParamGroup()
    {
        Params=new ParamList();
    }

    public JSONObject ToJSON()
    {
        JSONObject object=new JSONObject();
        try {
            object.put("Name", Name);
            object.put("Params", Params.ToJSON());
        }
        catch (Exception ignore)
        {}
        return object;
    }

    public void FromJSON(JSONObject object) {
        try {
            Name = object.optString("Name");
            Params.FromJSON(object.optString("Params"));
        } catch (Exception ignore) {
        }
    }
}
