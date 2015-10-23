package training.eduonix.weatherapp;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;

import training.eduonix.custom.AppUtils;
import training.eduonix.custom.Constants;


public class WeatherNowFragment extends Fragment {

    View weatherNowView;

    private static String selectedLocation = "";
    private static String weatherAPIUrl = "";
    private static String weatherIconUrl;


    ProgressDialog progressDialog;

    // TODO: Rename and change types and number of parameters
    public static WeatherNowFragment newInstance(String location) {
        WeatherNowFragment fragment = new WeatherNowFragment();
        selectedLocation = location;

        return fragment;
    }

    public WeatherNowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("WeatherNowFragment", "onResume") ;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("WeatherNowFragment","onCreate") ;
        if (selectedLocation != null && selectedLocation.length() > 0) {
            weatherAPIUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + selectedLocation + "&appid=d71a5a9bd77fc1d2f3f46e3b322f7366";
            if (AppUtils.isOnline(getActivity())) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage(getActivity().getResources().getString(R.string.fetch_weather_details));
                progressDialog.show();

                new WeatherNowTask().execute(weatherAPIUrl);
            } else {
                AppUtils.showAlert(getActivity(),getActivity().getResources().getString(R.string.no_network_title),
                        getActivity().getResources().getString(R.string.no_network_message));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        weatherNowView = inflater.inflate(R.layout.fragment_weather_now, container, false);

        return weatherNowView;
    }

    public class WeatherNowTask extends AsyncTask<String, String, String> {

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
                cancel(true);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                            String formattedDate = df.format(cal.getTime());

                            TextView dateView = (TextView) weatherNowView.findViewById(R.id.date);
                            dateView.setText(formattedDate);

                            String dayOfTheWeek = (String) android.text.format.DateFormat.format("EEEE", cal.getTime());//Thursday
                            TextView dayView = (TextView) weatherNowView.findViewById(R.id.day);
                            dayView.setText(dayOfTheWeek);


                            JSONArray array = jsonObject.getJSONArray("weather");
                            String weatherIconName = array.getJSONObject(0).getString("icon");
                            if (weatherIconName != null && weatherIconName.length() > 0) {
                                weatherIconUrl = "http://openweathermap.org/img/w/" + weatherIconName + ".png";
                                new DownloadImageTask((ImageView) weatherNowView.findViewById(R.id.weatherImage))
                                        .execute(weatherIconUrl);
                            }

                            String desc = array.getJSONObject(0).getString("description");
                            TextView descriptionView = (TextView) weatherNowView.findViewById(R.id.description);
                            descriptionView.setText(desc);

                            String temperature = jsonObject.getJSONObject("main").getString("temp");
                            double tempValue = Double.parseDouble(temperature);
                            tempValue = tempValue + AppUtils.TEMP_KELVIN_VALUE;


                            TextView temperatureView = (TextView) weatherNowView.findViewById(R.id.tempValue);
                            temperatureView.setText(AppUtils.getUpdatedTemperatureValue(getActivity(), tempValue));

                            String humidity = jsonObject.getJSONObject("main").getString("humidity");
                            Log.e("humidity", "" + humidity);
                            TextView humidityView = (TextView) weatherNowView.findViewById(R.id.humidityValue);
                            humidityView.setText(humidity + "%");

                            String pressure = jsonObject.getJSONObject("main").getString("pressure");
                            Log.e("pressure", "" + pressure);
                            TextView pressureView = (TextView) weatherNowView.findViewById(R.id.pressureValue);
                            pressureView.setText(pressure + "hPa");

                            String windSpeed = jsonObject.getJSONObject("wind").getString("speed");
                            Log.e("wind speed", "" + windSpeed);
                            TextView windSpeedView = (TextView) weatherNowView.findViewById(R.id.windSpeedValue);
                            windSpeedView.setText(windSpeed + "mph");

                        }
                        else
                        {
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
            protected void onCancelled (String s){
                super.onCancelled(s);
                progressDialog.hide();
                AppUtils.showAlert(getActivity(),getActivity().getResources().getString(R.string.error_title),
                        getActivity().getResources().getString(R.string.error_message));
            }
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
                ImageView iconImageView = (ImageView) weatherNowView.findViewById(R.id.weatherImage);
                iconImageView.setImageBitmap(result);
            }
        }

    }
