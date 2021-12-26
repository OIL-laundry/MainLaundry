package com.example.oil_laundry.Adapters;

public class OILCalendar {
    public Integer day,month,year,hour;

    public OILCalendar(){
        this.day=0;
        this.month=0;
        this.year=0;
        this.hour=0;
    }



    public String toString(){
        return (day+"/"+month+"/"+year+" "+hour+":00");
    }
    public String toString2(){
        return (day+","+month+","+year);
    }
}
