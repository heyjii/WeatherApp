package training.eduonix.weatherapp;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import training.eduonix.adapters.LocationListAdapter;
import training.eduonix.custom.Constants;
import training.eduonix.datamanager.LocationDataManager;
import training.eduonix.datamodel.LocationModel;

public class AddLocationFragment extends Fragment implements LocationListener,View.OnClickListener {

    private LocationManager locationManager;
    private Location currentLocation = null;
    String cityName = "";
    boolean isAutoLocationEnabled = true;
    private ProgressBar locationProgressBar ;
    private ArrayList<LocationModel> locationArray = new ArrayList<LocationModel>();
    private EditText enteredLocation;
    private ImageView addLocation;
    private ListView locationList;
    private LocationListAdapter locationListAdapter;
    boolean isCurrentLocationAdded = false ;
    private LocationDataManager locationDataManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View locationView = inflater.inflate(R.layout.fragment_add_location, container, false);

        locationProgressBar =(ProgressBar)locationView.findViewById(R.id.progressBar);

        locationManager = (LocationManager)
                getActivity().getSystemService(Context.LOCATION_SERVICE);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_APPEND);
        isAutoLocationEnabled = sharedPreferences.getBoolean(Constants.KEY_AUTO_LOCATION_ENABLED, true);

        if (isAutoLocationEnabled) {
            showSettingsAlert();
        } else {

            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 1000, 10, this);
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000, 10, this);
            locationProgressBar.setVisibility(View.VISIBLE);

        }

        //Load EditText
        enteredLocation = (EditText) locationView.findViewById(R.id.enter_location);

        //Add location icon
        addLocation = (ImageView) locationView.findViewById(R.id.add_location_icon);
        addLocation.setOnClickListener(this);

        //Load ListView
        locationList = (ListView) locationView.findViewById(R.id.location_list);

        locationDataManager = new LocationDataManager(getActivity());
        locationDataManager.open();
        locationArray = (ArrayList<LocationModel>) locationDataManager.getAllLocations();

        //Adapter for ListView
        locationListAdapter = new LocationListAdapter(getActivity(),R.id.location_list,locationArray);

        //set the adapter to the list
        locationList.setAdapter(locationListAdapter);


        return locationView;
    }

    @Override
    public void onResume() {

        super.onResume();


        ((HomeActivity) getActivity()).setActionBarTitle(getActivity().getResources().getString(R.string.add_location_fragment_title));

        if (isAutoLocationEnabled && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 1000, 10, this);
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000, 10, this);
            locationProgressBar.setVisibility(View.VISIBLE);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    getLastBestLocation();
                }
            }, 10000);
        }
        else{

            locationProgressBar.setVisibility(View.GONE);

        }
    }


    @Override
    public void onLocationChanged(Location location) {

        locationProgressBar.setVisibility(View.GONE);

        currentLocation = location;

        getLastBestLocation() ;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }
    @Override
    public void onProviderEnabled(String s) {

    }
    @Override
    public void onProviderDisabled(String s) {

    }

    /**
     * @return the last know best location
     */
    private Location getLastBestLocation() {
        locationProgressBar.setVisibility(View.GONE);

        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) {
            GPSLocationTime = locationGPS.getTime();
        }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if (0 < GPSLocationTime - NetLocationTime) {
            currentLocation = locationGPS;
        } else {
            currentLocation = locationNet;
        }

        /*------- To get city name from coordinates -------- */
        Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses;
        if (currentLocation != null) {
            try {
                addresses = gcd.getFromLocation(currentLocation.getLatitude(),
                        currentLocation.getLongitude(), 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        addCurrentLocationToList() ;
        showLocationMessage();
        return currentLocation;
    }

    private void showLocationMessage() {
        if (currentLocation != null) {
            String message = "Your Location is:\n Lattitude : " + currentLocation.getLatitude() + "\n Longitude: " + currentLocation.getLongitude();

            if (cityName.length() > 0) {
                message = message + "\n City name - " + cityName;
            }
           // Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                getActivity());

        alertDialog.setTitle("Fetch Location");

        alertDialog
                .setMessage("Do you want to fetch your location using GPS automatically?");

        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getActivity().startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        isAutoLocationEnabled = false;
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.add_location_icon){

            String location = enteredLocation.getText().toString();
            if(location.equalsIgnoreCase("")){

                Toast.makeText(getActivity(),R.string.empty_location_alert,Toast.LENGTH_SHORT).show();
            }
            else{

                LocationModel locationModel = locationDataManager.createLocation(location,0);


                locationArray.add(locationArray.size(),locationModel);
                locationListAdapter.notifyDataSetChanged();

                //Reset the edit text
                enteredLocation.setText("");
            }
        }

    }

    private void addCurrentLocationToList()
    {
        if(cityName != null && cityName.length() > 0 && !isCurrentLocationAdded)
        {

            LocationModel locationModel = locationDataManager.createLocation(cityName,1);

            locationArray.add(locationArray.size(),locationModel);
            isCurrentLocationAdded = true ;
            locationListAdapter.notifyDataSetChanged();
        }
    }
}
