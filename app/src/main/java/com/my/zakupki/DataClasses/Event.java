package com.my.zakupki.DataClasses;

import org.json.JSONObject;

public class Event {

    public String Date="";
    public String Content="";

    public JSONObject ToJSON()
    {
        JSONObject object=new JSONObject();
        try {
            object.put("Date", Date);
            object.put("Content", Content);
        }
        catch (Exception ignore)
        {}
        return object;
    }

    public void FromJSON(JSONObject object) {
        try {
            Date = object.optString("Date");
            Content = object.optString("Content");
        } catch (Exception ignore) {
        }
    }
}
