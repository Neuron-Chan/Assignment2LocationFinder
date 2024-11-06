package com.example.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "locations.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_LOCATION = "location";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_LOCATION + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ADDRESS + " TEXT NOT NULL, " +
                COLUMN_LATITUDE + " REAL NOT NULL, " +
                COLUMN_LONGITUDE + " REAL NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Example: If you added a new column, use ALTER TABLE to add it
            db.execSQL("ALTER TABLE " + TABLE_LOCATION + " ADD COLUMN new_column_name TEXT");
        }
        // If you need to handle other version upgrades, you can add more if-else statements
    }


    public void addLocation(String address, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        db.insert(TABLE_LOCATION, null, values);
    }

    public double[] getLocationByAddress(String address) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LOCATION, new String[]{COLUMN_LATITUDE, COLUMN_LONGITUDE},
                COLUMN_ADDRESS + "=?", new String[]{address}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            double latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE));
            cursor.close();
            return new double[]{latitude, longitude};
        }
        return null; // Not found
    }

    public void deleteLocation(String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATION, COLUMN_ADDRESS + "=?", new String[]{address});
    }

    public void updateLocation(String oldAddress, String newAddress, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, newAddress);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        db.update(TABLE_LOCATION, values, COLUMN_ADDRESS + "=?", new String[]{oldAddress});
    }

    public void fetchLocations() {
        String[][] locations = {
                // Add your predefined locations here
                {"Downtown Toronto", "43.65107", "-79.347015"},
                {"Scarborough", "43.7701", "-79.2627"},
                {"Mississauga", "43.5890", "-79.6441"},
                {"Brampton", "43.7315", "-79.7626"},
                {"Markham", "43.8561", "-79.3370"},
                {"Ajax", "43.8503", "-79.0205"},
                {"Pickering", "43.8484", "-79.1070"},
                {"Oshawa", "43.8978", "-78.8653"},
                {"Richmond Hill", "43.8775", "-79.4370"},
                {"Vaughan", "43.8390", "-79.5080"},
                {"Toronto Pearson International Airport", "43.6777", "-79.6248"},
                {"Etobicoke", "43.6132", "-79.5232"},
                {"Oakville", "43.4443", "-79.6877"},
                {"Burlington", "43.3256", "-79.7990"},
                {"Hamilton", "43.2557", "-79.8711"},
                {"Milton", "43.5235", "-79.8795"},
                {"Georgetown", "43.6481", "-79.9186"},
                {"Woodbridge", "43.8170", "-79.5584"},
                {"Newmarket", "44.0502", "-79.4666"},
                {"Aurora", "44.0002", "-79.4502"},
                {"East York", "43.6884", "-79.3157"},
                {"North York", "43.7692", "-79.4153"},
                {"Thornhill", "43.8181", "-79.4340"},
                {"East Gwillimbury", "44.1000", "-79.4667"},
                {"King City", "44.0169", "-79.6454"},
                {"Caledon", "43.8499", "-79.7476"},
                {"Maple", "43.8464", "-79.5151"},
                {"Toronto Islands", "43.6230", "-79.3832"},
                {"York", "43.6745", "-79.4680"},
                {"Toronto Zoo", "43.8345", "-79.1877"},
                {"High Park", "43.6465", "-79.4633"},
                {"Yorkdale Shopping Centre", "43.7246", "-79.4260"},
                {"Queen Street West", "43.6463", "-79.4154"},
                {"St. Lawrence Market", "43.6490", "-79.3704"},
                {"Scarborough Town Centre", "43.7756", "-79.2807"},
                {"York University", "43.7615", "-79.5004"},
                {"Dufferin Grove Park", "43.6613", "-79.4443"},
                {"Chinguacousy Park", "43.6845", "-79.7597"},
                {"Gus Harris Park", "43.6542", "-79.4408"},
                {"Kew Gardens", "43.6691", "-79.2970"},
                {"Trinity Bellwoods Park", "43.6487", "-79.4172"},
                {"Rouge National Urban Park", "43.8280", "-79.2167"},
                {"Spadina Avenue", "43.6665", "-79.4002"},
                {"Riverdale Park", "43.6735", "-79.3590"},
                {"Danforth", "43.6780", "-79.3060"},
                {"The Beaches", "43.6704", "-79.2934"},
                {"Little Italy", "43.6508", "-79.4310"},
                {"Chinatown", "43.6500", "-79.3965"},
                {"Cabbagetown", "43.6671", "-79.3634"},
                {"Greektown", "43.6798", "-79.3591"},
                {"Roncesvalles", "43.6552", "-79.4544"},
                {"Little Portugal", "43.6451", "-79.4335"},
                {"Danforth Village", "43.6856", "-79.3023"},
                {"Leslieville", "43.6645", "-79.3480"},
                {"Kensington Market", "43.6538", "-79.4028"},
                {"Parkdale", "43.6338", "-79.4614"},
                {"Liberty Village", "43.6282", "-79.4265"},
                {"Corso Italia", "43.6831", "-79.4465"},
                {"Rosedale", "43.6715", "-79.3746"},
                {"Forest Hill", "43.6881", "-79.4093"},
                {"Yorkville", "43.6705", "-79.3921"},
                {"Etobicoke Creek", "43.5933", "-79.6017"},
                {"Mimico", "43.6043", "-79.4870"},
                {"Old Mill", "43.6498", "-79.5034"},
                {"Scarborough Bluffs", "43.6832", "-79.2253"},
                {"Cedarvale Park", "43.6711", "-79.4333"},
                {"Sherwood Park", "43.6931", "-79.3716"},
                {"Stanley Park", "43.6761", "-79.4104"},
                {"Mount Pleasant Cemetery", "43.7096", "-79.3801"},
                {"Coronation Park", "43.6265", "-79.4533"},
                {"Bickford Park", "43.6532", "-79.4197"},
                {"Palace Pier", "43.6095", "-79.4860"},
                {"The Glen", "43.6759", "-79.3885"},
                {"The Islands", "43.6226", "-79.3774"},
                {"Don Valley", "43.7108", "-79.3668"},
                {"Humber Bay", "43.6106", "-79.4908"},
                {"Morningside Park", "43.7854", "-79.1773"},
                {"Ashbridges Bay Park", "43.6683", "-79.3037"},
                {"Guildwood Park", "43.7332", "-79.1810"},
                {"Centennial Park", "43.6313", "-79.6239"},
                {"Eglinton Flats", "43.6889", "-79.4578"},
                {"Rosedale Valley", "43.6715", "-79.3680"},
                {"Etobicoke Lakeshore", "43.6050", "-79.5211"},
                {"The Distillery District", "43.6540", "-79.3606"},
                {"Woodbine Beach", "43.6683", "-79.2964"},
                {"High Park Zoo", "43.6466", "-79.4635"},
                {"Riverdale Farm", "43.6681", "-79.3668"},
                {"St. Clair Avenue West", "43.6884", "-79.4158"},
                {"Sunnybrook Park", "43.7272", "-79.3600"},
                {"Bayview Village", "43.7703", "-79.3754"},
                {"Rattlesnake Point", "43.4551", "-79.9664"},
                {"Guelph", "43.5505", "-80.2482"},
                {"Eaton Centre", "43.6545", "-79.3806"},
                {"Scarborough Bluffs", "43.6832", "-79.2253"},
                {"Mimico Waterfront Park", "43.6052", "-79.5143"},
                {"Don Mills", "43.7651", "-79.3495"},
                {"Danforth Music Hall", "43.6761", "-79.3531"},
                {"Yorkdale Mall", "43.7248", "-79.4270"},
                {"Rogers Centre", "43.6415", "-79.3892"},
                {"Royal Ontario Museum", "43.6672", "-79.3949"},
                {"St. Joseph's Health Centre", "43.6291", "-79.4584"},
        };

        SQLiteDatabase db = this.getWritableDatabase();
        for (String[] location : locations) {
            addLocation(location[0], Double.parseDouble(location[1]), Double.parseDouble(location[2]));
        }
    }
}
