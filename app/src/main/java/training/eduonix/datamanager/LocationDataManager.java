package training.eduonix.datamanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import training.eduonix.database.WeatherAppSQLiteOpenHelper;
import training.eduonix.datamodel.LocationModel;

/**
 * Created by AndroDev on 22-10-2015.
 */
public class LocationDataManager {

    // Database fields
    private SQLiteDatabase database;
    private WeatherAppSQLiteOpenHelper dbHelper;
    private String[] allColumns = { WeatherAppSQLiteOpenHelper.COLUMN_ID,
            WeatherAppSQLiteOpenHelper.COLUMN_NAME,  WeatherAppSQLiteOpenHelper.COLUMN_AUTO_LOCATION};

    public LocationDataManager(Context context) {
        dbHelper = new WeatherAppSQLiteOpenHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public LocationModel createLocation(String locationName,int autoLocation) {
        ContentValues values = new ContentValues();
        values.put(WeatherAppSQLiteOpenHelper.COLUMN_NAME, locationName);
        values.put(WeatherAppSQLiteOpenHelper.COLUMN_AUTO_LOCATION, autoLocation);
        long insertId = database.insert(WeatherAppSQLiteOpenHelper.TABLE_LOCATIONS, null,
                values);
        Cursor cursor = database.query(WeatherAppSQLiteOpenHelper.TABLE_LOCATIONS,
                allColumns, WeatherAppSQLiteOpenHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        LocationModel newLocation = cursorToComment(cursor);
        cursor.close();
        return newLocation;
    }

    public void deleteLocation(LocationModel comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(WeatherAppSQLiteOpenHelper.TABLE_LOCATIONS, WeatherAppSQLiteOpenHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<LocationModel> getAllLocations() {
        List<LocationModel> locations = new ArrayList<LocationModel>();

        Cursor cursor = database.query(WeatherAppSQLiteOpenHelper.TABLE_LOCATIONS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LocationModel location = cursorToComment(cursor);
            locations.add(location);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return locations;
    }

    private LocationModel cursorToComment(Cursor cursor) {
        LocationModel location = new LocationModel();
        location.setId(cursor.getLong(0));
        location.setName(cursor.getString(1));
        location.setAutoLocation(cursor.getInt(2));
        return location;
    }


}
