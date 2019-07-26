package com.dvtweather.tshepo.dvtweather;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Tshepo
 */

public class FragPassport extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_passport, container, false);

        return view;
    }


    /**
     * fragment variable and widget declaration.
     */
    private LinearLayout relativeLayout;
    private RelativeLayout searchCity;
    private View view;

    private ImageView pImageView;
    private TextView pDatetime;
    private TextView pCity;
    private TextView pCloudcover;
    private TextView pPress;
    private TextView pCurrent;
    private TextView pMinMax;
    private Button button;

    private Date date;

    private JSONMain main;
    private JSONOther other;
    private JSONSystem system;
    private JSONWeather weather;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        relativeLayout = (LinearLayout) view.findViewById(R.id.frag_passport);
        relativeLayout.setBackgroundColor(Color.WHITE);

        getWidgets(view);

    }

	/**
     * gets widgets for XML.
     */
    private void getWidgets(View v)
    {
        pDatetime   = (TextView) v.findViewById(R.id.p_datetime);
        pCity       = (TextView) v.findViewById(R.id.p_city);

        pImageView = (ImageView) v.findViewById(R.id.p_image);

        pCloudcover = (TextView) v.findViewById(R.id.p_cloudcondition);
        pPress = (TextView) v.findViewById(R.id.p_pressure);

        pCurrent    = (TextView) v.findViewById(R.id.p_current);
        pMinMax     = (TextView) v.findViewById(R.id.p_min_max);

        button      = (Button) v.findViewById(R.id.btnEnter);

        searchCity  = (RelativeLayout) v.findViewById(R.id.searchLayout);

        searchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Thol' ukuthi",Toast.LENGTH_LONG).show();
                Search();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Search();
            }
        });
    }

	/**
     * using locations api, retrieves locations latitude and longitude
	 * to parse to the weather api later.
     */
    private void Search() {
        searchCity.setOnClickListener(new View.OnClickListener() {
		 //button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
			{
			try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(getActivity());
                    startActivityForResult(intent, 1);
                } 
				catch (GooglePlayServicesRepairableException e) 
				{
                    e.printStackTrace();
                } 
				catch (GooglePlayServicesNotAvailableException e) 
				{
                    e.printStackTrace();
                }

            }
        });

    }

    public LatLng genLatLng = null;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK)
            {
                //retrieve the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                //Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());

                //Saving the search latitude and longitude
                genLatLng = place.getLatLng();
				//formatting string to parse to the API
                String latlng = "lat=" + genLatLng.latitude +"&lon="+ genLatLng.longitude;
                System.out.println(latlng);
                getJSONobject(latlng);
			
			}
            else if (resultCode == PlaceAutocomplete.RESULT_ERROR)
            {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                Toast.makeText(getContext(), "Input is not valid..", Toast.LENGTH_LONG).show();
                Log.e("Tag", status.getStatusMessage());

            }
            else if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(getContext(), "Cancelled..", Toast.LENGTH_LONG).show();
            }
        }
    }



    private SimpleDateFormat todayFormat = new SimpleDateFormat("HH:mm \tEEEE, dd/MMMM/yyyy ");

    /**
     * retrives information from the api and populates JSON > Java objects
     * @param coord = contains a formatted text containing latitude and longitude
     *              uses volley libraries
     */
    public void getJSONobject(String coord)
    {
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
        }, new Response.ErrorListener()
		{
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jor);
		
    }

	/**
     * displays select object information on app widgets
     */
    private void displayWeatherInfo()
    {
        date = new Date(System.currentTimeMillis());
        String d = "<b><i> Last refreshed: </i></b> " + todayFormat.format(date);
        pDatetime.setText(Html.fromHtml(d));

        pCity.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        pCity.setText(other.getName() + ", " + system.getCountrycode());

        int resID = getResources().getIdentifier("drawable/img_" + weather.getIcon(), null, getActivity().getPackageName());
        @SuppressWarnings("deprecation")
        Drawable weatherIcon = getResources().getDrawable(resID);
        pImageView.setImageDrawable(weatherIcon);

        pCloudcover.setText(weather.getDescription().toUpperCase(Locale.ENGLISH));

        String ap = "<small>AIR PRESSURE:</small> " + (int)main.getPressure()+ " hPa";
        pPress.setText(Html.fromHtml(ap));

        pCurrent.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
        pCurrent.setText((int)main.getCurrent()+ "\u2103");

        String tmp = "<i>Night</i> \u21D3: " + main.getMin() + "\u2103 || " + "<i>Day</i> \u21D1: " + main.getMax() + "\u2103";
        pMinMax.setText(Html.fromHtml(tmp));
    }
}