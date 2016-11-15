package com.example.home.starbuzz;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DrinkCategoryActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = getListView();
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<>(
                this,                                       //<-Текущая активность
                android.R.layout.simple_list_item_1,        //<-Встроенный ресурс макета. Он приказывает адаптеру
                Drink.drinks);    //<-массив                    массива отображать каждый элемент массива в виде надписи

        listView.setAdapter(listAdapter);
    }

    @Override
    protected void onListItemClick(ListView listView,
                                   View itemView,
                                   int position,
                                   long id) {
        Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
        intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int) id);
        startActivity(intent);
    }
}
