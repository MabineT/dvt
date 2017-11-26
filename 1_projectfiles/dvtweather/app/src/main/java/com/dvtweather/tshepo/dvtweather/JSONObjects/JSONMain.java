package com.dvtweather.tshepo.dvtweather.JSONObjects;

/**
 * Created by Tshepo
 */

public class JSONMain {

    /*
    * class member variables
    * */
    private double current = 0.0;

    private double pressure = 0.0;
    private double humidity = 0.0;

    private double min = 0.0;
    private double max = 0.0;

    /**
     * constructor to retrieve main object.
     * @param c = current temperature
     * @param p = pressure
     * @param h = humidity
     * @param n = minimum temperature
     * @param x = maximum temperature
     */
    public JSONMain(double c, double p, double h, double n, double x)
    {
        this.current = c;

        this.pressure = p;
        this.humidity = h;

        this.min = n;
        this.max = x;
    }

    @Override
    public String toString() {
        String res = "";

        res += "CURRENT: " + getCurrent() + "\n";
        res += "PRESSURE: " + getPressure() + "\n";
        res += "HUMIDITY: " + getHumidity() + "\n";
        res += "MIN: " + getMin() + "\n";
        res += "MAX: " + getMax() + "\n";

        return  res;
    }

    /*
        * member accessors
        * */
    public double getCurrent() {
        return current;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}
