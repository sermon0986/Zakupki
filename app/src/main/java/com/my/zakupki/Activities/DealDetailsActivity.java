package com.my.zakupki.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.my.zakupki.Common;
import com.my.zakupki.R;
import com.my.zakupki.Storage;

public class DealDetailsActivity extends AppCompatActivity {

    private boolean wasFavClick=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Детали закупки");

    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent();
//        if(wasFavClick)
//            setResult(RESULT_OK, intent);
//        else
//            setResult(RESULT_CANCELED, intent);
//        super.onBackPressed();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);

        Drawable drawable = menu.findItem(R.id.action_favorites).getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, getResources().getColor(android.R.color.white));
        menu.findItem(R.id.action_favorites).setIcon(drawable);

        MenuItem star = menu.findItem(R.id.action_favorites);

        if(Common.Favorites.Items.contains(Common.CurrentDeal))
            star.setIcon(R.drawable.favorites_orange);
        else {
            Drawable stardrawable = getResources().getDrawable(R.drawable.assets_favorites);
            stardrawable = DrawableCompat.wrap(stardrawable);
            DrawableCompat.setTint(stardrawable, getResources().getColor(android.R.color.white));
            star.setIcon(stardrawable);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.action_favorites) {
            wasFavClick=true;
            Common.NeedRefreshMain=true;
            if(!Common.Favorites.Items.contains(Common.CurrentDeal))
                Common.Favorites.Items.add(Common.CurrentDeal);
            else
                Common.Favorites.Items.remove(Common.CurrentDeal);
        }

        supportInvalidateOptionsMenu();
        Storage.SaveFavorites(DealDetailsActivity.this);

        return super.onOptionsItemSelected(item);
    }


}
