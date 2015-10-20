package training.eduonix.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import training.eduonix.custom.Constants;
import training.eduonix.weatherapp.R;
import training.eduonix.weatherapp.WeatherActivity;

/**
 * Created by AndroDev on 11-10-2015.
 */
public class LocationListAdapter extends ArrayAdapter<String> {

    Context mContext;
    ArrayList<String> locationList;

    public LocationListAdapter(Context context, int resource, ArrayList<String> locations) {
        super(context, resource, locations);

        mContext = context;
        locationList = locations;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // assign the view we are converting to a local variable
        View v = convertView;
        TextView tvName = null;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_item, null);

        // Lookup view for data population
        tvName = (TextView) v.findViewById(R.id.location_text);

        /*
         * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 */
        final String location = locationList.get(position);

        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent weatherIntent = new Intent(mContext, WeatherActivity.class) ;
                weatherIntent.putExtra(Constants.KEY_SELECTED_LOCATION,location) ;
                mContext.startActivity(weatherIntent);
            }
        });

        tvName.setText(location);

        return v;
    }
}
