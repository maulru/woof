package com.desai.vatsal.mydynamiccalendar;


public class EventModel {

    private String strDate;
    private String strStartTime;
    private String strEndTime;
    private String strName;
    private int image = -1;


    public EventModel(String strDate, String strStartTime, String strName) {
        this.strDate = strDate;
        this.strStartTime = strStartTime;
   
        this.strName = strName;
    }

    public EventModel(String strDate, String strStartTime, String strName, int image) {
        this.strDate = strDate;
        this.strStartTime = strStartTime;
       
        this.strName = strName;
        this.image = image;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrStartTime() {
        return strStartTime;
    }

    public void setStrStartTime(String strStartTime) {
        this.strStartTime = strStartTime;
    }

    

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
