package com.example.oil_laundry;

public class OILCalendar {
    public Integer day,month,year,hour;

    OILCalendar(){
        this.day=0;
        this.month=0;
        this.year=0;
        this.hour=0;
    }

/*    public Integer getDay(int dayOfMonth) {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth(int i) {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear(int year) {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }*/

    public String toString(){
        return (day+"/"+month+"/"+year+" "+hour+":00");
    }
    public String toString2(){
        return (day+","+month+","+year);
    }
}
