package com.example.home.workout;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutDetailFragment extends Fragment {

    private long workoutId;

    public WorkoutDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (savedInstanceState != null)
            workoutId = savedInstanceState.getLong("workoutId");        //Воставновление номера пункта упражнения
        else {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            StopwatchFragment stopwatchFragment = new StopwatchFragment();
            ft.replace(R.id.stopwatch_container, stopwatchFragment);        //Заменитиь фрагмент во фрейме
            ft.addToBackStack(null);                                        //Добавить транзакцию в стек возврата
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();                    //Метод getView() получает коневой объект View фрагмента
        if (view != null)                         //Далее полученный объект используется для получения ссылок
        {                                         //на надписи, предназначенные для названия и описания комплекса упражнений
            TextView title = (TextView) view.findViewById(R.id.textTitle);
            Workout workout = Workout.workouts[(int) workoutId];
            title.setText(workout.getName());
            TextView description = (TextView) view.findViewById(R.id.textDescription);
            description.setText(workout.getDescription());

            ImageView photo = (ImageView) view.findViewById(R.id.photo);
            photo.setImageResource(workout.getImageResourse());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanseState) {      //Метод Save вызывается перед уничтожением фрагмента
        savedInstanseState.putLong("workoutId", workoutId);

    }

    public void setWorkout(long id) {           //Метод используется автиностью для передачи
        this.workoutId = id;                    //значения индентификатора фрагменту.
    }
}
