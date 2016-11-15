package com.example.home.starbuzz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TopLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, // <-  Представлене, на котором был сделан щелчок
                                    View view,               //     спиское представление в данном случае
                                    int position,
                                    long id) {
                switch (position)
                {
                    case 0:
                        Intent intent = new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }

            }
        };
        //Добавление слушателя к списковому представлению.
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }


}
