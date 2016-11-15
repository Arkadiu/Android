package com.example.home.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alexander on 10.10.2016.
 */

public class StarbuzzDataBaseHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "starbuzz";       //Имя базы данных
    private static final int DB_VERSION = 2;                //Версия базы данных

    StarbuzzDataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);          //параметр null для работы с курсорами
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private static void insertDrink(SQLiteDatabase db, String name, String description, int ResourceID) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", ResourceID);
        db.insert("DRINK", null, drinkValues);
    }

    private static void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE DRINK ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);"
            );

            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam", R.drawable.capuchino);
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filtered);
            insertDrink(db, "Moccachino", "test test test", R.drawable.filtered);
        }
        if (oldVersion < 2) {
            //Код добавление нового столбца
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC");
        }
    }

}
