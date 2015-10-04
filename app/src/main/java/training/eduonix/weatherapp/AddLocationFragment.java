package training.eduonix.weatherapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import training.eduonix.custom.AppLocationService;

public class AddLocationFragment extends Fragment {

    Button btnGPSShowLocation;
    Button btnNWShowLocation;
    AppLocationService appLocationService;

    private String GPS_Privider = "GPS" ;
    private String Network_Provider = "Network" ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View locationView = inflater.inflate(R.layout.fragment_add_location, container, false);

        appLocationService = new AppLocationService(
                getActivity());

        btnGPSShowLocation = (Button) locationView.findViewById(R.id.showGPSLocationBtn);
        btnGPSShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Location gpsLocation = appLocationService
                        .getLocation(LocationManager.GPS_PROVIDER );

                LocationManager locationManager = (LocationManager) getActivity()
                        .getSystemService(Context.LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    if (gpsLocation != null) {
                        double latitude = gpsLocation.getLatitude();
                        double longitude = gpsLocation.getLongitude();
                        Toast.makeText(
                                getActivity(),
                                "Mobile Location (GPS): \nLatitude: " + latitude
                                        + "\nLongitude: " + longitude,
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(
                                getActivity(),
                                "GPS Location is not available",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    showSettingsAlert(GPS_Privider);
                }

            }
        });

        btnNWShowLocation = (Button) locationView.findViewById(R.id.showNWLocationBtn);
        btnNWShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Location nwLocation = appLocationService
                        .getLocation(LocationManager.NETWORK_PROVIDER);

                if (nwLocation != null) {
                    double latitude = nwLocation.getLatitude();
                    double longitude = nwLocation.getLongitude();
                    Toast.makeText(
                            getActivity(),
                            "Mobile Location (NW): \nLatitude: " + latitude
                                    + "\nLongitude: " + longitude,
                            Toast.LENGTH_LONG).show();
                } else {
                    showSettingsAlert(Network_Provider);
                }

            }
        });

        return locationView;
    }

    public void showSettingsAlert(final String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                getActivity());

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (provider.equalsIgnoreCase(GPS_Privider)) {
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            getActivity().startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(
                                    Settings.ACTION_WIRELESS_SETTINGS);
                            getActivity().startActivity(intent);
                        }

                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

}
