package com.my.zakupki.DataClasses;


import org.json.JSONObject;

public class Deal {

    public String Number="";
    public String PublishType="";
    public String Publisher="";
    public String Description="";
    public String Price="";
    public String Currency="";
    public String CurrentStatus="";
    public String PublishDate="";
    public String UpdateDate="";

    public String UrlPrintForm="";
    public String UrlCommonInfo="";
    public String UrlDocuments="";


    public JSONObject ToJSON()
    {
        JSONObject object=new JSONObject();
        try {
            object.put("Number", Number);
            object.put("PublishType", PublishType);
            object.put("Publisher", Publisher);
            object.put("Description", Description);
            object.put("Price", Price);
            object.put("Currency", Currency);
            object.put("CurrentStatus", CurrentStatus);
            object.put("PublishDate", PublishDate);
            object.put("UpdateDate", UpdateDate);

        }
        catch (Exception ignore)
        {}
        return object;
    }

    public void FromJSON(JSONObject object) {
        try {
            Number = object.optString("Number");
            PublishType = object.optString("PublishType");
            Publisher = object.optString("Publisher");
            Description = object.optString("Description");
            Price = object.optString("Price");
            Currency = object.optString("Currency");
            CurrentStatus = object.optString("CurrentStatus");
            PublishDate = object.optString("PublishDate");
            UpdateDate = object.optString("UpdateDate");
        } catch (Exception ignore) {
        }
    }

}
