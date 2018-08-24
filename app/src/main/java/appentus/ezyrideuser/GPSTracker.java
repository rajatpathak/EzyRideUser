package appentus.ezyrideuser;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSTracker implements  LocationListener {

    private final Context mContext;

    // flag for GPS status
    public boolean isGPSEnabled = false;
    public String code = "";
    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;
    public String address = "";
    List<Address> addresses;
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    Geocoder geocoder;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
        geocoder = new Geocoder(context, Locale.getDefault());
        getLocation();
    }

    /**
     * Function to get the user's current location
     *
     * @return
     */
    public String getDialCode() throws IOException {
        Log.v("longitude", "=" + longitude);
        if (latitude == 0.0 || longitude == 0.0) {
            code = "91";
        } else  if (location != null)
        {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            code = addresses.get(0).getCountryCode();
        }else {
            code = "91";
        }
        return code;
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            Log.v("isGPSEnabled", "=" + isGPSEnabled);

            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Log.v("isNetworkEnabled", "=" + isNetworkEnabled);

            if (isGPSEnabled == false && isNetworkEnabled == false) {
                Toast.makeText(mContext, "Gps is off", Toast.LENGTH_SHORT).show();
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    location = null;
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        }
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            address = addresses.get(0).getLocality() + " , " + addresses.get(0).getCountryName();
                            code = addresses.get(0).getCountryCode();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    location=null;
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                address = addresses.get(0).getLocality() + " , " + addresses.get(0).getCountryName();
                                code = addresses.get(0).getCountryCode();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Stop using GPS listener Calling this function will stop using GPS in your
     * app
     * */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public String getAddress(){
        if (location != null){
            address = addresses.get(0).getAddressLine(0);
        }
        return address;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog On pressing Settings button will
     * lauch Settings Options
     * */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    private String getCountryCode() {
        //Helper.log(TAG, "getCountryCode");

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, String> countryCodeTask = new AsyncTask<Void, Void, String>() {

            final float latitude = (float) location.getLatitude();
            final float longitude = (float) location.getLongitude();
            // Helper.log(TAG, "latitude: " +latitude);
            List<Address> addresses = null;
            Geocoder gcd = new Geocoder(mContext);
            String code = null;

            @Override
            protected String doInBackground(Void... params) {
                try {
                    addresses = gcd.getFromLocation(latitude, longitude, 1);
                    code = addresses.get(0).getCountryCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return code;
            }

            @Override
            protected void onPostExecute(String code) {
                super.onPostExecute(code);
            }

        };
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            countryCodeTask.execute();
        } else {
            countryCodeTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        return code;
    }



}