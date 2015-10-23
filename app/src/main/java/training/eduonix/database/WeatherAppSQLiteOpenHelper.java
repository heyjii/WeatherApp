package training.eduonix.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AndroDev on 22-10-2015.
 */
public class WeatherAppSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_LOCATIONS = "locations";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AUTO_LOCATION = "auto_location";

    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String LOCATION_TABLE_CREATE = "create table "
            + TABLE_LOCATIONS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " text not null,"+ COLUMN_AUTO_LOCATION
            + " integer)";

    public WeatherAppSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LOCATION_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
