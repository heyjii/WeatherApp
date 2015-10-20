package training.eduonix.weatherapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class WeatherThisWeekFragment extends Fragment {

    private View WeatherThisWeekFragment ;
    private static String selectedLocation = "";


    public static WeatherThisWeekFragment newInstance(String location) {
        WeatherThisWeekFragment fragment = new WeatherThisWeekFragment();
        selectedLocation = location ;
        return fragment;
    }

    public WeatherThisWeekFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        WeatherThisWeekFragment = inflater.inflate(R.layout.fragment_weather_this_week, container, false);
        return WeatherThisWeekFragment ;
    }


}
