package com.dvtweather.tshepo.dvtweather.JSONObjects;

/**
 * Created by Tshepo
 */

public class JSONOther {

    /*
    * class variables
    * */
    private String base = "";
    private double speed = 0.0;

    private long date = 0L;
    private double id = 0.0;

    private String name = "";

    /**
     * constructor to retrieve main object.
     * @param b = base
     * @param s = wind speed
     * @param d = date
     * @param i = id
     * @param n = local city/town name
     */
    public JSONOther(String b, double s, long d, double i, String n)
    {
        this.base = b;
        this.speed = s;

        this.date = d;
        this.id = i;

        this.name = n;
    }


    @Override
    public String toString() {
        String res = "";

        res += "BASE: " + getBase() + "\n";
        res += "SPEED: " + getSpeed() + "\n";
        res += "DATE: " + getDate() + "\n";
        res += "ID: " + getId() + "\n";
        res += "NAME: " + getName() + "\n";

        return res;
    }

    /**
     * member accessors
     */
    public String getBase() {
        return base;
    }

    public double getSpeed() {
        return speed;
    }


    public long getDate() {
        return date;
    }

    public double getId() {
        return id;
    }


    public String getName() {
        return name;
    }
}
