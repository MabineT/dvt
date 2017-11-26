package com.dvtweather.tshepo.dvtweather.JSONObjects;

/**
 * Created by Tshepo
 */

public class JSONWeather {

    /**
     * class variables
     */
    private int id = 0;
    private String main = "";

    private String description = "";
    private String icon = "";


    /**
     * constructor to retrieve main object
     * @param i = id
     * @param m = main
     * @param d = description
     * @param n = icon
     */
    public JSONWeather(int i, String m, String d, String n)
    {
        this.id = i;
        this.main = m;

        this.description = d;
        this.icon = n.substring(0, (n.length()-1));
    }

    @Override
    public String toString() {
        String res = "";

        res += "ID: " + getId() + "\n";
        res += "MAIN: " + getMain() + "\n";
        res += "DESCRIPTION: " + getDescription() + "\n";
        res += "ICON: " + getIcon() + "\n";

        return res;
    }


    /**
     * member accessors
     */
    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
