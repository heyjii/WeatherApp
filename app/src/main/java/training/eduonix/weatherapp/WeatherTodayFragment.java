package training.eduonix.weatherapp;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import training.eduonix.adapters.WeatherDetailsListAdapter;
import training.eduonix.custom.AppUtils;
import training.eduonix.custom.Constants;


public class WeatherTodayFragment extends Fragment {

    private View WeatherTodayView;
    private static String selectedLocation = "";
    private static String weatherAPIUrl = "";
    private static String weatherIconUrl;
    int timeCount = 8; // Number of time in a day with 3 hour separation.

    ProgressDialog progressDialog;

    private ArrayList<HashMap<String, String>> weatherList = new ArrayList<>();
    private ListView weatherDetailsListView;
    private WeatherDetailsListAdapter weatherDetailsListAdapter;


    public static WeatherTodayFragment newInstance(String location) {
        WeatherTodayFragment fragment = new WeatherTodayFragment();
        selectedLocation = location;
        return fragment;
    }

    public WeatherTodayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("WeatherTodayFragment", "onCreate") ;

        if (selectedLocation != null && selectedLocation.length() > 0) {
            weatherAPIUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + selectedLocation + "&mode=json&appid=d71a5a9bd77fc1d2f3f46e3b322f7366";
            if (AppUtils.isOnline(getActivity())) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage(getActivity().getResources().getString(R.string.fetch_weather_details));
                progressDialog.show();

                new WeatherTodayTask().execute(weatherAPIUrl);
            } else {
                AppUtils.showAlert(getActivity(), getActivity().getResources().getString(R.string.no_network_title),
                        getActivity().getResources().getString(R.string.no_network_message));
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("WeatherTodayFragment", "onResume") ;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        WeatherTodayView = inflater.inflate(R.layout.fragment_weather_today, container, false);

        //Load ListView
        weatherDetailsListView = (ListView) WeatherTodayView.findViewById(R.id.weatherlistView);

        //Adapter for ListView
        weatherDetailsListAdapter = new WeatherDetailsListAdapter(getActivity(), R.id.weatherlistView, weatherList);

        //set the adapter to the list
        weatherDetailsListView.setAdapter(weatherDetailsListAdapter);
        return WeatherTodayView;
    }

    public class WeatherTodayTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost method = new HttpPost(params[0]);
                HttpResponse response = httpClient.execute(method);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity);
                } else {
                    return "No String";
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                // cancel(true);
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            try {
                progressDialog.hide();
                if (s != null && s.length() > 0) {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject != null) {

                        String code = jsonObject.getString("cod");
                        if (code != null && code.length() > 0 && code.equalsIgnoreCase(Constants.KEY_SUCCESS)) {


                            JSONArray rootArray = jsonObject.getJSONArray("list");
                            JSONArray array = rootArray.getJSONObject(0).getJSONArray("weather");
                            String weatherIconName = array.getJSONObject(0).getString("icon");

                            if (weatherIconName != null && weatherIconName.length() > 0) {
                                weatherIconUrl = "http://openweathermap.org/img/w/" + weatherIconName + ".png";
                                new DownloadImageTask((ImageView) WeatherTodayView.findViewById(R.id.weatherImage))
                                        .execute(weatherIconUrl);
                            }
                            int weatherCount = jsonObject.getInt("cnt");


                            for (int nIndex = 0; nIndex < timeCount; nIndex++) {
                                HashMap<String, String> hashMap = new HashMap<>();
                                String dateString = rootArray.getJSONObject(nIndex).getString("dt");
                                long time = Long.parseLong(dateString + "000");
                                Calendar cal = Calendar.getInstance();
                                cal.setTimeInMillis(time);
                                int hour = cal.get(Calendar.HOUR);
                                int minute = cal.get(Calendar.MINUTE);
                                int AmPm = cal.get(Calendar.AM_PM);
                                String hourFormat;
                                if (AmPm == 1) {
                                    hourFormat = "AM";
                                } else {
                                    hourFormat = "PM";
                                }
                                String timeValue = "" + hour + ":" + minute + " " + hourFormat;

                                String temp = rootArray.getJSONObject(nIndex).getJSONObject("main").getString("temp");

                                double tempValue = Double.parseDouble(temp);
                                tempValue = tempValue + AppUtils.TEMP_KELVIN_VALUE;
                                String temperatureValue = AppUtils.getUpdatedTemperatureValue(getActivity(),tempValue) ;

                                hashMap.put(timeValue, temperatureValue);
                                weatherList.add(hashMap) ;
                                Log.e("" + timeValue, "" + temperatureValue);
                            }
                            weatherDetailsListAdapter.notifyDataSetChanged();

                        } else {
                            AppUtils.showAlert(getActivity(), getActivity().getResources().getString(R.string.error_title),
                                    jsonObject.getString("message"));
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            progressDialog.hide();
            AppUtils.showAlert(getActivity(), getActivity().getResources().getString(R.string.error_title),
                    getActivity().getResources().getString(R.string.error_message));
        }

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {

                progressDialog.hide();
                ImageView iconImageView = (ImageView) WeatherTodayView.findViewById(R.id.weatherImage);
                iconImageView.setImageBitmap(result);
            }
        }
    }
}
