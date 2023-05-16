package com.example.procalendar.Calendar_Alarm;

public class WorkAlarm
{
    private Integer ID;
    private String date;
    private String note;
    public WorkAlarm()
    {
        date = "1/1/1979";
        note = "";
    }
    public  WorkAlarm(String _date, String _note)
    {
        date = _date;
        note = _note;
    }
    public  WorkAlarm(int _ID, String _date, String _note)
    {
        ID = _ID;
        date = _date;
        note = _note;
    }
    public String getDate() {return date;}
    public String getNote() {return note;}
    public Integer getID() {return ID;}
    public void setDate(String _date) {date = _date;}
    public void setNote(String _note) {note = _note;}
    public void setID(Integer id) {ID = id;}
}
