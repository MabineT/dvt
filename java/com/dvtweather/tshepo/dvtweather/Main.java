package com.dvtweather.tshepo.dvtweather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Main extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    /**
     * base widget declaration
     */
    protected TabLayout tabLayout;
    protected ViewPager viewPager;


    int tabImage[] = {R.drawable.ic_my_location_white_36dp, R.drawable.ic_location_on_white_36dp};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grantMultiplePermissions();

        tabLayoutOnCreation();

        System.out.println(ALL_GRANTED);
    }


    /*
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    */

    String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE};


    private static final int MY_PERMISSIONS_ALL = 650;
    public static boolean ALL_GRANTED = false;


    public boolean hasPermissions(Context context, String... permissions) {


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {

                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void grantMultiplePermissions() {
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, MY_PERMISSIONS_ALL);
        } else {
            ALL_GRANTED = true;
        }
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }



    private void tabLayoutOnCreation() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);


        tabLayout.addTab(tabLayout.newTab().setText(R.string.label_mylocation).setIcon(tabImage[0]));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.label_passport).setIcon(tabImage[1]));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);

        PagerMain adapter = new PagerMain(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        //this line right here, \/ beastly..
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(this);
    }


    public static String getCountryName(Context c, double lat, double lng)
    {
        String name = "";

        Geocoder geocoder = new Geocoder(c, Locale.getDefault());
        try
        {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);

            //String streetAddress = obj.getAddressLine(0) + "\n";

            //streetAddress += obj.getThoroughfare() + "\n";	//

            //streetAddress += obj.getLocality() + "\n";		//city
            String streetAddress = obj.getCountryName();


            //streetAddress += obj.getPhone() + "\n";
            //streetAddress += obj.getPostalCode() + "";

            //streetAddress.replace("null", "   ");
            Toast.makeText(c , streetAddress , Toast.LENGTH_LONG).show();
            name = streetAddress;
            //System.out.println(streetAddress);
            //address.setText(streetAddress);

        }
        catch (IOException e)
        {
            System.out.println("failed to obtain");
            e.printStackTrace();
        }

        return  name;
    }
}
