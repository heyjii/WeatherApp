package training.eduonix.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public  class AppUtils {

    public static double TEMP_KELVIN_VALUE = -273.15;

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void showAlert(Context context,String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.show();
    }

    public static String getUpdatedTemperatureValue(Context context,double tempInCelcius) {
        String tempValue;
        double temp;
        SharedPreferences sharedPreferences = context.
                getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_APPEND);
        String value = sharedPreferences.getString(Constants.KEY_TEMPERATURE_UNIT, Constants.KEY_TEMP_CELCIUS);
        if (value.equalsIgnoreCase(Constants.KEY_TEMP_CELCIUS)) {
            temp = tempInCelcius;
            tempValue = String.format("%.2f", temp);
            tempValue = tempValue + "°C";
        } else {
            temp = (tempInCelcius * 1.8) + 32;
            tempValue = String.format("%.2f", temp);
            tempValue = tempValue + "°F";
        }
        return tempValue;
    }
}
