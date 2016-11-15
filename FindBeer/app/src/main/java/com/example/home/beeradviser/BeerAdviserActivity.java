package com.example.home.beeradviser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class BeerAdviserActivity extends AppCompatActivity {

    private BeerExpert beerExpert = new BeerExpert();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickFindBeer(View view) {
        TextView brands = (TextView) findViewById(R.id.brands);
        Spinner color = (Spinner) findViewById(R.id.color);
        String beerType = String.valueOf(color.getSelectedItem());

        StringBuilder recommendBrand = new StringBuilder("Рекомендуем сорта пива:");
        for (String s : beerExpert.getBrands(beerType))
            recommendBrand.append("\n- " + s);
        brands.setText(recommendBrand);
    }
}
