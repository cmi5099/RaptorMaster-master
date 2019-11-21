package org.tensorflow.lite.examples.classification.sqlite;

import android.provider.BaseColumns;

public final class SensorReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private SensorReaderContract() {}

    /* Inner class that defines the table contents */
    public static class SensorEntry implements BaseColumns {
        public static final String TABLE_NAME = "data_sensor";
        public static final String COLUMN_NAME_ACC_X = "acc_x";
        public static final String COLUMN_NAME_ACC_Y = "acc_y";
        public static final String COLUMN_NAME_ACC_Z = "acc_z";
        public static final String COLUMN_NAME_LIGHT = "light";
        public static final String COLUMN_NAME_PRESSURE = "pressure";
        public static final String COLUMN_NAME_RELATIVE_HUMIDITY = "humidity";
        public static final String COLUMN_NAME_TEMPERATURE = "temperature";
    }

    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SensorEntry.TABLE_NAME + " (" +
                    SensorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SensorEntry.COLUMN_NAME_LIGHT + " REAL," +
                    SensorEntry.COLUMN_NAME_PRESSURE + " REAL,"+
                    SensorEntry.COLUMN_NAME_RELATIVE_HUMIDITY + " REAL," +
                    SensorEntry.COLUMN_NAME_TEMPERATURE + " REAL," +
                    SensorEntry.COLUMN_NAME_ACC_X + " REAL," +
                    SensorEntry.COLUMN_NAME_ACC_Y + " REAL," +
                    SensorEntry.COLUMN_NAME_ACC_Z + " REAL)";


    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SensorEntry.TABLE_NAME;



}