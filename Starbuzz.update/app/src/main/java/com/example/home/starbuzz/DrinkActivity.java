package com.example.home.starbuzz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKNO = "drinkNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);

        //Создание курсора
        try {
            SQLiteOpenHelper starbuzzDataBaseHelper = new StarbuzzDataBaseHelper(this);
            SQLiteDatabase db = starbuzzDataBaseHelper.getWritableDatabase();

            Cursor cursor = db.query("DRINK",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id = ?",
                    new String[]{Integer.toString(drinkNo)},
                    null, null, null);

            if (cursor.moveToFirst()) {
                //Получение данных напика из курсора
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoID = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);

                //Заполнение названия напитка
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText);

                //Заполнение описание напитка
                TextView description = (TextView) findViewById(R.id.description);
                description.setText(descriptionText);

                //Заполнение изображения напитка
                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoID);
                photo.setContentDescription(nameText);           //Необходимо, чтобы повысить уровень доступности приложения

                //Заполнение флажка любимого напитка
                CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);
            }
            cursor.close();
            db.close();

        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "DataBase unavailable1", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void isFavoriteClicked(View view) {
        int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);
        new UpdateDrinkTask().execute(drinkNo);
    }

    private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean> {

        ContentValues drinkValues;

        @Override
        protected void onPreExecute() {
            //Код, предшествующий выполнению задачи
            CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
            drinkValues = new ContentValues();
            drinkValues.put("FAVORITE", favorite.isChecked());
        }

        @Override
        protected Boolean doInBackground(Integer... drinks) {
            //Код, выполняемый в фоновом потоке
            int drinkNo = drinks[0];
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDataBaseHelper(DrinkActivity.this);

            try {
                SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
                db.update("DRINK", drinkValues, "_id = ?", new String[]{Integer.toString(drinkNo)});
                db.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            //Код, выполняемый при завершении задачи
            if (!success) {
                Toast toast = Toast.makeText(DrinkActivity.this, "DataBase unavailable2", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
