package com.example.home.workout;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements WorkoutListFragment.WorkoutListListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*WorkoutDetailFragment frag =
                (WorkoutDetailFragment) getFragmentManager().findFragmentById(R.id.detail_frag);
        frag.setWorkout(1);*/

    }

    @Override
    public void itemClicked(long id) {
        //Здесь размещается код отображения подробной информации
        //Этот метод определяется в слушателе.
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {                                         //Выполняется если существует контейнер (планшет)
            WorkoutDetailFragment detail = new WorkoutDetailFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();   //Начало транзакции объекта
            detail.setWorkout(id);
            ft.replace(R.id.fragment_container, detail);                        //Заменить фрагмент
            ft.addToBackStack(null);                                            //и Добавить в стек возврата
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);        //Включить анимацию растворения и появления фрагментов
            ft.commit();                                                        //Закрепить транзакцию
        } else {
            Intent intent = new Intent(this, DetailActivity.class);             //Выполняется, если нет контейнера-фрейма (телефон)
            intent.putExtra(DetailActivity.EXTRA_WORKOUT_ID, (int) id);
            startActivity(intent);
        }
    }
}