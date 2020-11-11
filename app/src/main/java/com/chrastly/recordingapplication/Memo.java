package com.chrastly.recordingapplication;

import org.json.JSONException;
import org.json.JSONObject;

public class Memo {

    private String Id;
    private String title;
    private String description;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;

    public void setId(String Id){
        this.Id = Id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }



    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getStartDate(){
        return startDate;
    }

    public String getStartTime(){
        return startTime;
    }

    public String getEndDate(){
        return endDate;
    }

    public String getEndTime(){
        return endTime;
    }

}
