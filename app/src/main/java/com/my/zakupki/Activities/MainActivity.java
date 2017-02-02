package com.my.zakupki.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.my.zakupki.Adapters.RecyclerViewAdapter_Main;
import com.my.zakupki.DataClasses.Deal;
import com.my.zakupki.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter_Main recyclerViewAdapter;
    private LinearLayoutManager layoutManager;
    private FloatingActionButton fab;

    private List<Deal> MainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDialog dialog = new MaterialDialog.Builder(MainActivity.this)
                        .title("Search")
                        .positiveText(R.string.dialogs_yes)
                        .negativeText(R.string.dialogs_no)
                        .show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        MainList=new ArrayList<>();

        Deal deal1=new Deal();
        deal1.Number="124352345";
        deal1.Publisher="MTC";
        deal1.Price="10 000";
        deal1.Currency="российский рубль";
        deal1.PublishType="Открытый аукцион";
        deal1.CurrentStatus ="Подача заявок";
        deal1.Description="Оказание услуг связи";
        deal1.PublishDate="31.01.2017";
        deal1.UpdateDate="01.02.2017";

        Deal deal2=new Deal();
        deal2.Number="34631236";
        deal2.Publisher="Megaphone";
        deal2.Price="25 000";
        deal2.Currency="российский рубль";
        deal2.PublishType="Открытый аукцион";
        deal2.CurrentStatus ="Подача заявок";
        deal2.Description="Оказание услуг связи";
        deal2.PublishDate="31.01.2017";
        deal2.UpdateDate="01.02.2017";

        Deal deal3=new Deal();
        deal3.Number="2362342";
        deal3.Publisher="Beeline";
        deal3.Price="1 000 000";
        deal3.Currency="российский рубль";
        deal3.PublishType="Открытый аукцион";
        deal3.CurrentStatus ="Подача заявок";
        deal3.Description="Оказание услуг связи";
        deal3.PublishDate="31.01.2017";
        deal3.UpdateDate="01.02.2017";

        Deal deal4=new Deal();
        deal4.Number="96764422";
        deal4.Publisher="Tele2";
        deal4.Price="25 000";
        deal4.Currency="российский рубль";
        deal4.PublishType="Открытый аукцион";
        deal4.CurrentStatus ="Подача заявок";
        deal4.Description="Оказание услуг связи";
        deal4.PublishDate="31.01.2017";
        deal4.UpdateDate="01.02.2017";

        MainList.add(deal1);
        MainList.add(deal2);
        MainList.add(deal3);
        MainList.add(deal4);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerViewAdapter = new RecyclerViewAdapter_Main(MainList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);

        //view
        recyclerViewAdapter = new RecyclerViewAdapter_Main(MainList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_favorites) {

        } else if (id == R.id.nav_search) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
