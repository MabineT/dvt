package com.dvtweather.tshepo.dvtweather.JSONObjects;

/**
 * Created by Tshepo
 */

public class JSONSystem {

    /*
    * class member variables
    * */
    private int type = 0;
    private int id = 0;

    private double message = 0.0;
    private String countrycode = "";

    private long sunrise = 0L;
    private long sunset = 0L;


    @Override
    public String toString() {
        String res = "";

        res += "TYPE: " + getType() + "\n";
        res += "ID: " + getId() + "\n";
        res += "MESSAGE: " + getMessage() + "\n";
        res += "CODE: " + getCountrycode() + "\n";
        res += "SUNSET: " + getSunrise() + "\n";
        res += "SUNSET: " + getSunset() + "\n";

        return res;
    }

    /**
     * constructor to retrieve main object.
     * @param t = system type
     * @param i = system id
     * @param m = system message
     * @param c = country code
     * @param r = sunrise
     * @param s = sunset
     */
    public JSONSystem(int t, int i, double m, String c, long r, long s)
    {
        this.type = t;
        this.id  = i;

        this.message = m;
        this.countrycode = c;

        this.sunrise = r;
        this.sunset = s;
    }

    /**
     * member accessors
     */
    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }


    public double getMessage() {
        return message;
    }

    public String getCountrycode() {
        return countrycode;
    }


    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

}
