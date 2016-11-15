package com.example.home.workout;


import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutListFragment extends ListFragment {

    static interface WorkoutListListener{
        void itemClicked(long id);
    }

    private WorkoutListListener listener;

    public WorkoutListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] names = new String[Workout.workouts.length];
        for (int i = 0; i < names.length; i++)
            names[i] = Workout.workouts[i].getName();

        ArrayAdapter<String> listAdapter =
                new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, names);
        setListAdapter(listAdapter);                        //<- Связать адаптер массива со списковым представлением.

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {               //Вызывается при присоединеини фрагмента к автиности
        super.onAttach(activity);
        this.listener = (WorkoutListListener) activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(listener != null)                                    //Сообщить слушателю о том, что на одном из вариантов ListView сделан щелчок
            listener.itemClicked(id);
    }
}
