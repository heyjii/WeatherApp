package training.eduonix.weatherapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class WeatherTodayFragment extends Fragment {

    private View WeatherTodayFragmentView ;
    private static String selectedLocation = "";


    public static WeatherTodayFragment newInstance(String location) {
        WeatherTodayFragment fragment = new WeatherTodayFragment();
        selectedLocation = location ;
        return fragment;
    }

    public WeatherTodayFragment() {
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
        WeatherTodayFragmentView = inflater.inflate(R.layout.fragment_weather_today, container, false);
        return WeatherTodayFragmentView ;
    }
}
