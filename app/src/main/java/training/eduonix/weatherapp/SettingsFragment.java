package training.eduonix.weatherapp;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import training.eduonix.custom.Constants;


public class SettingsFragment extends Fragment implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {


    private TextView aboutText;
    private Switch temperatureSwitch;
    private Switch autoLocationSwitch;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_settings, container, false);

        //About TextView
        aboutText = (TextView) fragmentView.findViewById(R.id.about_text);
        aboutText.setOnClickListener(this);

        //Temperature Unit
        temperatureSwitch = (Switch) fragmentView.findViewById(R.id.temp_unit_swich);
        temperatureSwitch.setOnCheckedChangeListener(this);


        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(Constants.KEY_TEMPERATURE_UNIT,Constants.KEY_TEMP_FARENHEIT) ;
        if(value.equalsIgnoreCase(Constants.KEY_TEMP_CELCIUS))
        {
            temperatureSwitch.setChecked(true);
        }
        else
        {
            temperatureSwitch.setChecked(false);
        }
        //Auto Location
        autoLocationSwitch = (Switch) fragmentView.findViewById(R.id.auto_location_switch);
        autoLocationSwitch.setOnCheckedChangeListener(this);

        setHasOptionsMenu(false);

        return fragmentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((HomeActivity)getActivity()).
                setActionBarTitle(getActivity().getResources().getString(R.string.settings_fragment_title));
    }

    @Override
    public void onClick(View view) {


        if (view.getId() == R.id.about_text) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    getActivity());

            alertDialog.setTitle(getActivity().getResources().getString(R.string.about));

            alertDialog
                    .setMessage(getActivity().getResources().getString(R.string.about_content));

            alertDialog.show();


        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        SharedPreferences sharedPreferences = getActivity().
                getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_APPEND);

        if (compoundButton.getId() == R.id.temp_unit_swich) {

            if (b) {

                sharedPreferences.edit().putString(Constants.KEY_TEMPERATURE_UNIT,
                        Constants.KEY_TEMP_CELCIUS);
                sharedPreferences.edit().commit();

            } else {

                sharedPreferences.edit().putString(Constants.KEY_TEMPERATURE_UNIT,
                        Constants.KEY_TEMP_FARENHEIT);
                sharedPreferences.edit().commit();
            }

        } else if (compoundButton.getId() == R.id.auto_location_switch) {

            if (b) {

                sharedPreferences.edit().putBoolean(Constants.KEY_AUTO_LOCATION_ENABLED, true);
                sharedPreferences.edit().commit();

            } else {

                sharedPreferences.edit().putBoolean(Constants.KEY_AUTO_LOCATION_ENABLED, true);
                sharedPreferences.edit().commit();
            }

        }


    }
}
