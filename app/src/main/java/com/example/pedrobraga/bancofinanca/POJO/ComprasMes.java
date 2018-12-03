package com.example.pedrobraga.bancofinanca.POJO;

/**
 * Created by pedro.braga on 02/12/2018.
 */

public class ComprasMes {


    private String month;
    private String year;
    private Float total;


    public String getMonthYear() {

        return this.month + "/" + this.year;

    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
