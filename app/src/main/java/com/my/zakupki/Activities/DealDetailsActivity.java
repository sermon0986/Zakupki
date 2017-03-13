package com.my.zakupki.Activities;

import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.my.zakupki.Common;
import com.my.zakupki.R;
import com.my.zakupki.Storage;

public class DealDetailsActivity extends AppCompatActivity {

    private TextView TVnumber, TVpubtype, TVpublisher, TVdesc, TVprice, TVcurstatus, TVpublish_date, TVupd_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Детали закупки");

        TVnumber=(TextView)findViewById(R.id.TVnumber);
        TVpubtype=(TextView)findViewById(R.id.TVpubtype);
        TVpublisher=(TextView)findViewById(R.id.TVpublisher);
        TVdesc=(TextView)findViewById(R.id.TVdesc);
        TVprice=(TextView)findViewById(R.id.TVprice);
        TVcurstatus=(TextView)findViewById(R.id.TVcurstatus);
        TVpublish_date=(TextView)findViewById(R.id.TVpublish_date);
        TVupd_date=(TextView)findViewById(R.id.TVupd_date);

        TVnumber.setText(Common.CurrentDeal.Number);
        TVpubtype.setText(Common.CurrentDeal.PublishType);
        TVpublisher.setText(Common.CurrentDeal.Publisher);
        TVdesc.setText(Common.CurrentDeal.Description);
        TVprice.setText(Common.CurrentDeal.Price);
        TVcurstatus.setText(Common.CurrentDeal.CurrentStatus);
        TVpublish_date.setText(Common.CurrentDeal.PublishDate);
        TVupd_date.setText(Common.CurrentDeal.UpdateDate);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);

        Drawable drawable = menu.findItem(R.id.action_favorites).getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, getResources().getColor(android.R.color.white));
        menu.findItem(R.id.action_favorites).setIcon(drawable);

        MenuItem star = menu.findItem(R.id.action_favorites);

        if(Common.Favorites.IndexOf(Common.CurrentDeal)!=-1)
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
            Common.NeedRefreshMain=true;
            if(Common.Favorites.IndexOf(Common.CurrentDeal)==-1)
                Common.Favorites.Items.add(Common.CurrentDeal);
            else
                Common.Favorites.Items.remove(Common.CurrentDeal);
        }

        supportInvalidateOptionsMenu();
        Storage.SaveFavorites(DealDetailsActivity.this);

        return super.onOptionsItemSelected(item);
    }


}
