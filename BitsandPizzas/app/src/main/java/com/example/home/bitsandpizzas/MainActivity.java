package com.example.home.bitsandpizzas;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;

import com.example.home.bitsandpizzas.Fragment.PastaFragment;
import com.example.home.bitsandpizzas.Fragment.PizzaFragment;
import com.example.home.bitsandpizzas.Fragment.StoresFragment;
import com.example.home.bitsandpizzas.Fragment.TopFragment;

public class MainActivity extends Activity {

    private ShareActionProvider shareActionProvider;
    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;                                  //Добавить приватную переменную для DrawerLayout, так как объект будет использоваться в других методах
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Код, выполняемый при щелчке на элементе списка.
            selectItem(position);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titles = getResources().getStringArray(R.array.titles);
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Заполнение ListView
        drawerList.setAdapter(new ArrayAdapter<String>(                             //Режим simple_list_item_activiated_1 означает, что вариант,
                this, android.R.layout.simple_list_item_activated_1, titles         //на котором щелкнул пользова-тель, выделяется подсветкой.
        ));

        drawerList.setOnItemClickListener(new DrawerItemClickListener());           //Добавить новый экземляр onItemClickListener к списковому
        //представлению выдвижной панели
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");                //Если активность была уничтожена и создается заново, взять значение currentPosition из предыдущего состояния активности
            setActionBarTitle(currentPosition);                                     //и использовать его для назначения заголовка панели действий
        } else
            selectItem(0);                                                          //При исходном создании MainActivity использовать метод selectItem() для отображения TopFragment

        //Создавние ActionBarDrawerToggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //Код, выполняемый при закрытии выдвижной панели
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //Код, выполняемый при открытии выдвижной панели.
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);                               //Назначить ActionBarDrawerToggle слушателем выдвижной панели DrawerLayout
        getActionBar().setDisplayHomeAsUpEnabled(true);                              //Включить кнопку вверх, чтобы ее можно было использовать для управления выдвижной панелью
        getActionBar().setHomeButtonEnabled(true);

        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        //Код, который должен выполняться при изменении в стеке возврата
                        FragmentManager fragMan = getFragmentManager();
                        Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
                        if (fragment instanceof TopFragment)
                            currentPosition = 0;
                        if (fragment instanceof  PizzaFragment)
                            currentPosition = 1;
                        if (fragment instanceof PastaFragment)
                            currentPosition = 2;
                        if (fragment instanceof StoresFragment)
                            currentPosition = 3;
                        setActionBarTitle(currentPosition);
                        drawerList.setItemChecked(currentPosition, true);           //Вывести текст на панели действий и выделить правильный вариант в списке на выдвижной панели
                    }
                }
        );
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Синхронизировать состония выключателя после onRestoreInstanceState
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))                                    //Чтобы объект ActionBarDrawerToggle реагировал на щелчки
            return true;
        //Код для остальных элементов действий
        switch (item.getItemId()) {
            case R.id.action_create_order:
                //Код создания заказа
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;                                                            //Сообщает Андроид, что щелчок на элементе отработан
            case R.id.action_settings:
                //Код запуска активности с настройками
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {                                    //Вызывается при каждом вызове invalidateOptionMenu()
        //Если выдвижная панель открыта, скрыть элменты, связанные с контентом
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);                       //Действие Share становится невидимым, если выдвижная панель открыта, и видимым при закрытой панели
        return super.onPrepareOptionsMenu(menu);
    }

    private void selectItem(final int position) {
        currentPosition = position;
        final Fragment fragment;
        switch (position) {
            case 1:
                fragment = new PizzaFragment();
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoresFragment();
                break;
            default:
                fragment = new TopFragment();
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();           //Вывести фрагмент с использованием транзакции фрагмента
        ft.replace(R.id.content_frame, fragment, "visible_fragment");               //назначается метка какой фрагмент виден
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);                //Для замены текущего фрагмента используется тарзакцийя фрагмента
        ft.commit();
        //Назначение заголовка панели действий
        setActionBarTitle(position);                                                //Вызвать метод setActionTitle() и передать ему позицию варрианта, на котором был сделан щелчок
        //Закрытие выдвижной панели
        drawerLayout.closeDrawer(drawerList);                                       //drawerList - выдвижная панель, связанная с DrawerLayout. Вызов приказывает объекту DrawerLayout закрыть панель drawerList

    }

    private void setActionBarTitle(int position) {
        String title;
        if (position == 0) {                                                    //Если пользователь выбрал вариант Home, в качестве текста заголовка используется имя приложения
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position];                                           //В противном случае получить из массива titles строку, соответствующую позиции выбранного варианта, и использовать ее.
        }
        getActionBar().setTitle(title);                                         //вывести заголово на панель действий.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Заполнение меню; эдементы (если они есть) добавляются на панель действий.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();       //Получить ссылку на провайдера действия передачи информации
        setIntent("This example text");                                                 //и присвоить ее приватной переменной. Затем вызвать метод
        return super.onCreateOptionsMenu(menu);                                         //SetIntent()
    }

    private void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);                                 //Мы создаем метод setIntent(), который создает интент
        intent.setType("text/plain");                                                   // и передаем его провайдеру действия передачи инфомарции
        intent.putExtra(Intent.EXTRA_TEXT, text);                                       // при помощи его метода setShareIntent()
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }


}