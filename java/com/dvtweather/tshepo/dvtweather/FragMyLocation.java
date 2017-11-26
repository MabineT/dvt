package com.dvtweather.tshepo.dvtweather;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dvtweather.tshepo.dvtweather.JSONObjects.JSONMain;
import com.dvtweather.tshepo.dvtweather.JSONObjects.JSONOther;
import com.dvtweather.tshepo.dvtweather.JSONObjects.JSONSystem;
import com.dvtweather.tshepo.dvtweather.JSONObjects.JSONWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Tshepo on 07/08/2017.
 */

public class FragMyLocation extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_mylocation, container, false);

        return view;
    }


    /**
     * fragment variable and widget declaration.
     */
    private LinearLayout relativeLayout;
    private View view;
    private SwipeRefreshLayout refreshLayout;
    private GPSTracker mGPS;

    private ImageView wImageView;
    private TextView wDatetime;
    private TextView wCity;
    private TextView wCloudcover;
    private TextView wPress;
    private TextView wCurrent;
    private TextView wMinMax;
    //private TextView wSunrise;
    //private TextView wSunset;

    private long currentTime;
    private Date date;


    private JSONMain main;
    private JSONOther other;
    private JSONSystem system;
    private JSONWeather weather;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        relativeLayout = (LinearLayout) view.findViewById(R.id.frag_mylocation);
        relativeLayout.setBackgroundColor(Color.WHITE);

        getWidgets(view);

        refreshLayout();


    }


    private void getWidgets(View v)
    {
        wDatetime   = (TextView) v.findViewById(R.id.w_datetime);
        wCity       = (TextView) v.findViewById(R.id.w_city);

        wImageView  = (ImageView) v.findViewById(R.id.w_image);

        wCloudcover = (TextView) v.findViewById(R.id.w_cloudcondition);
        wPress = (TextView) v.findViewById(R.id.w_pressure);

        wCurrent    = (TextView) v.findViewById(R.id.w_current);
        wMinMax     = (TextView) v.findViewById(R.id.w_min_max);

        //wSunrise    = (TextView) v.findViewById(R.id.w_sunrise);
        //wSunset     = (TextView) v.findViewById(R.id.w_sunset);

        /*
        * set labels to null?
        * */

    }

    private void refreshLayout()
    {
        mGPS = new GPSTracker(getContext());
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        refreshLayout.setColorSchemeResources(R.color.deepskyblue,  R.color.cornflowerblue, R.color.dodgerblue, R.color.cadetblue);


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(Main.ALL_GRANTED)
                {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getCurrentLocation();
                            refreshLayout.setRefreshing(false);
                        }
                    }, 1500);
                }
            }
        });
    }

    private SimpleDateFormat todayFormat = new SimpleDateFormat("HH:mm \tEEEE, dd/MMMM/yyyy ");
    //private SimpleDateFormat rise_set = new SimpleDateFormat("HH:mm");

    /**
     * get current device location in decimal form.
     */
    private void getCurrentLocation()
    {
        if (mGPS.canGetLocation) {
            mGPS.getLocation();
            String parser = "lat=" + mGPS.getLatitude() +"&lon="+ mGPS.getLongitude();
            getJSONdata(parser);
            System.out.println(mGPS.getLatitude() +"\n"+ mGPS.getLongitude());
        }
        else
        {
            Toast.makeText(getContext(), "unable to find", Toast.LENGTH_LONG).show();
            System.out.println("Unable");
        }
    }


    public void getJSONdata(String coord)
    {
        //volley api here
        //lat=-26.18379191&lon=27.98840935
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?%s&appid=74b118a2e76bfa7bee7ed2941525f768&units=metric", coord);

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONObject main_object = response.getJSONObject("main");

                    double curr = main_object.getDouble("temp");
                    double pres = main_object.getDouble("pressure");
                    double hum = main_object.getDouble("humidity");
                    double min = main_object.getDouble("temp_min");
                    double max = main_object.getDouble("temp_max");

                    main = new JSONMain(curr, pres, hum, min, max);
                    System.out.println(main.toString());

                    //****************

                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);

                    int w_id = object.getInt("id");
                    String w_main = object.getString("main");
                    String w_desc = object.getString("description");
                    String w_icon = object.getString("icon");

                    weather = new JSONWeather(w_id, w_main, w_desc, w_icon);
                    System.out.println(weather.toString());

                    //****************

                    JSONObject sys_object = response.getJSONObject("sys");

                    int type = sys_object.getInt("type");
                    int id = sys_object.getInt("id");
                    double mess = sys_object.getDouble("message");
                    String code = sys_object.getString("country");
                    long rise = sys_object.getLong("sunrise");
                    long set = sys_object.getLong("sunset");

                    system = new JSONSystem(type, id, mess, code, rise, set);
                    System.out.println(system.toString());

                    //****************

                    String base = response.getString("base");

                    JSONObject wind_object = response.getJSONObject("wind");

                    double speed = wind_object.getDouble("speed");

                    long curr_date = response.getLong("dt");
                    double o_id = response.getDouble("id");

                    String name = response.getString("name");

                    other = new JSONOther(base, speed, curr_date, o_id, name);
                    System.out.println(other.toString());

                    //****************

                    displayWeatherInfo();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jor);

        //displayWeatherInfo();
    }

    private void displayWeatherInfo()
    {
        /*
        wDatetime = (TextView) v.findViewById(R.id.w_datetime);
        wCity = (TextView) v.findViewById(R.id.w_city);
        wCloudcover = (TextView) v.findViewById(R.id.w_cloudcondition);
        wPress = (TextView) v.findViewById(R.id.w_rain_percentage);

        wCurrent = (TextView) v.findViewById(R.id.w_current);
        wMinMax = (TextView) v.findViewById(R.id.w_min_max);
        wSunrise    = (TextView) v.findViewById(R.id.w_sunrise);
        */

        //date = new Date(other.getDate());
        
		date = new Date(System.currentTimeMillis());
        String dt = "<b><i> Last refreshed: </i></b> " + todayFormat.format(date);
		wDatetime.setText(Html.fromHtml(dt));

		wCity.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        wCity.setText(other.getName() + ", " + Main.getCountryName(getContext(), mGPS.getLatitude(), mGPS.getLongitude()));
		
        /*
        Date rise = new Date(system.getSunrise());
        date = new Date(system.getSunrise());
        wSunrise.setText("rise: "+rise_set.format(rise));


        Date set = new Date(system.getSunset());
        date = new Date(system.getSunset());
        wSunset.setText("set: " + rise_set.format(set));
        */

        //wImageView.setImageResource();

        int resID = getResources().getIdentifier("drawable/img_" + weather.getIcon(), null, getActivity().getPackageName());
        @SuppressWarnings("deprecation")
        Drawable weatherIcon = getResources().getDrawable(resID);

        wImageView.setImageDrawable(weatherIcon);

        wCloudcover.setText(weather.getDescription().toUpperCase(Locale.ENGLISH));

		String ap = "<small>AIR PRESSURE:</small> " + (int)main.getPressure()+ " hPa";
        wPress.setText(Html.fromHtml(ap));

		wCurrent.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        wCurrent.setText((int)main.getCurrent()+ "\u2103");
		
		String mix_max = "<i>Night</i> \u21D3: " + main.getMin() + "\u2103 || " + "<i>Day</i> \u21D1: " + main.getMax() + "\u2103";
        wMinMax.setText(Html.fromHtml(mix_max));
    }





}


