package com.my.zakupki.DataClasses;

import org.json.JSONObject;

public class Event {

    public String Date="";
    public String Content="";
    public boolean Showed=false;
    public long UpdateTime;

    public JSONObject ToJSON()
    {
        JSONObject object=new JSONObject();
        try {
            object.put("Date", Date);
            object.put("Content", Content);
            object.put("Showed", Showed);
            object.put("UpdateTime", UpdateTime);
        }
        catch (Exception ignore)
        {}
        return object;
    }

    public void FromJSON(JSONObject object) {
        try {
            Date = object.optString("Date");
            Content = object.optString("Content");
            Showed = object.optBoolean("Showed", false);
            UpdateTime = object.optLong("UpdateTime");
        } catch (Exception ignore) {
        }
    }
}
