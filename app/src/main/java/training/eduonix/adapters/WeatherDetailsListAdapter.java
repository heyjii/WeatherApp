package training.eduonix.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import training.eduonix.weatherapp.R;

public class WeatherDetailsListAdapter extends ArrayAdapter{

    Context mContext ;
    ArrayList<HashMap<String,String>> weatherDetailsList ;

    public WeatherDetailsListAdapter(Context context, int resource, ArrayList<HashMap<String, String>> weatherList) {
        super(context, resource, weatherList);
        mContext = context ;
        weatherDetailsList = weatherList ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dateTime = null;
        TextView temperature = null;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_weather_details, null);

        // Lookup view for data population
        dateTime = (TextView) v.findViewById(R.id.dateTime);
        temperature = (TextView) v.findViewById(R.id.temperatureValue);

        HashMap<String, String> weatherMap = new HashMap<>() ;
        weatherMap = weatherDetailsList.get(position) ;

        if(weatherMap != null) {
            for (Map.Entry<String, String> entry : weatherMap.entrySet()) {
                String key = entry.getKey();
                dateTime.setText(key);
                String value = entry.getValue();
                temperature.setText(value);
            }
        }
        return v;
    }
}
