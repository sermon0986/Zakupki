package com.my.zakupki.DataClasses;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class EventList {

    public List<Event> Items;

    public EventList()
    {
        Items=new ArrayList<>();
    }

    public String ToJSON()
    {
        JSONArray array=new JSONArray();
        try {
            for(Event event:Items)
                array.put(event.ToJSON());
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
                Event event=new Event();
                event.FromJSON(array.getJSONObject(i));
                Items.add(event);
            }
        } catch (Exception ignore) {
        }
    }

}
