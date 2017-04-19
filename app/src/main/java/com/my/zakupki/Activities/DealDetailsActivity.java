package com.my.zakupki.Activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.my.zakupki.Common;
import com.my.zakupki.DataClasses.Event;
import com.my.zakupki.DataClasses.Param;
import com.my.zakupki.DataClasses.ParamGroup;
import com.my.zakupki.DataClasses.ParamList;
import com.my.zakupki.Net.Fz223PrintFormUrlBuilder;
import com.my.zakupki.Net.Fz44PrintFormUrlBuilder;
import com.my.zakupki.Net.PageLoaderCallbackInterface;
import com.my.zakupki.Net.RuleTreeFactory;
import com.my.zakupki.Net.UniZhournalUrlBuilder;
import com.my.zakupki.Net.WebUrlLoader;
import com.my.zakupki.R;
import com.my.zakupki.Storage;

import java.util.List;
import java.util.Map;

public class DealDetailsActivity extends AppCompatActivity {

    private RelativeLayout journal_title;
    private TableLayout journal_table;
    private ImageView journal_arrow;
    private ProgressBar journal_progress;

    private LinearLayout groupLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Детали закупки");

        journal_title=(RelativeLayout)findViewById(R.id.journal_title);
        journal_table=(TableLayout)findViewById(R.id.journal_table);
        journal_arrow=(ImageView)findViewById(R.id.journal_arrow);
        journal_progress=(ProgressBar)findViewById(R.id.journal_progress);

        groupLayout=(LinearLayout)findViewById(R.id.groupLayout);

        journal_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(journal_table.getVisibility()==View.VISIBLE) {
                    journal_table.setVisibility(View.GONE);
                    journal_arrow.setRotation(90);
                }else {
                    journal_table.setVisibility(View.VISIBLE);
                    journal_arrow.setRotation(0);
                }
            }
        });

        ViewJournal();
        ViewGroups();

        UniZhournalUrlBuilder uniZhournalUrlBuilder=new UniZhournalUrlBuilder();
        if(!Common.CurrentDeal.UrlCommonInfo.isEmpty()) {
            journal_progress.setVisibility(View.VISIBLE);
            uniZhournalUrlBuilder.InitUrl(Common.CurrentDeal.UrlCommonInfo);
            WebUrlLoader journalgetter = new WebUrlLoader(
                    DealDetailsActivity.this
                    , updateJournalInterface
                    , uniZhournalUrlBuilder.Build()
                    , Common.maClient
                    , RuleTreeFactory.INSTANCE.getTransformer("Uni_Zhournal")
            );
            Thread tr = new Thread(journalgetter);
            journalgetter.setId(tr.getId());
            tr.start();
        }

        if(Common.CurrentDeal.Law.equals("44-ФЗ")) {
            Fz44PrintFormUrlBuilder fz44PrintFormUrlBuilder = new Fz44PrintFormUrlBuilder();
            WebUrlLoader infogetter2 = new WebUrlLoader(
                    DealDetailsActivity.this
                    , updateInfoInterface
                    , fz44PrintFormUrlBuilder.Build()
                    , Common.maClient
                    , RuleTreeFactory.INSTANCE.getTransformer("Fz44_Test_1")
            );
            Thread tr2 = new Thread(infogetter2);
            infogetter2.setId(tr2.getId());
            tr2.start();
        }else {
            Fz223PrintFormUrlBuilder fz223PrintFormUrlBuilder=new Fz223PrintFormUrlBuilder();
            WebUrlLoader infogetter1 = new WebUrlLoader(
                    DealDetailsActivity.this
                    , updateInfoInterface
                    , fz223PrintFormUrlBuilder.Build()
                    , Common.maClient
                    , RuleTreeFactory.INSTANCE.getTransformer("Fz223_Test_1")
            );
            Thread tr1 = new Thread(infogetter1);
            infogetter1.setId(tr1.getId());
            tr1.start();
        }

    }

    private PageLoaderCallbackInterface updateInfoInterface=new PageLoaderCallbackInterface() {
        @Override
        public void onSuccess(Map<String, Object> resultSet) {

            List<Map<String,Object>> paramgroups = (List<Map<String,Object>>)resultSet.get("paramgroups");
            if(paramgroups.size()>0) {
                Common.CurrentDeal.ParamGroups.Items.clear();
                for (Map<String, Object> group : paramgroups) {
                    ParamGroup paramGroup=new ParamGroup();
                    paramGroup.Name=group.get("groupName").toString().trim();

                    List<Pair<String,String>> params = (List<Pair<String,String>>)group.get("keyValues");

                    for (int i=0;i<params.size();i++)
                    {
                        Param new_param=new Param();
                        new_param.Name=params.get(i).first;
                        new_param.Value=params.get(i).second;
                        paramGroup.Params.Items.add(new_param);
                    }

                    Common.CurrentDeal.ParamGroups.Items.add(paramGroup);
                }
                ViewGroups();
                if(Common.Favorites.IndexOf(Common.CurrentDeal)!=-1)
                    Storage.SaveFavorites(DealDetailsActivity.this);
            }
        }

        @Override
        public void onFail(String err) {

        }

        @Override
        public void onTimeOut() {

        }
    };

    private void ViewGroups()
    {
        groupLayout.removeAllViews();
        if(Common.CurrentDeal.ParamGroups.Items.size()>0) {
            for(int i=0;i<Common.CurrentDeal.ParamGroups.Items.size();i++)
                ViewGroup(Common.CurrentDeal.ParamGroups.Items.get(i), i==Common.CurrentDeal.ParamGroups.Items.size()-1);
        }
    }

    private void ViewGroup(ParamGroup paramGroup, boolean bottomSpace)
    {

        LayoutInflater inflater = LayoutInflater.from(this);
        View group_card = inflater.inflate(R.layout.param_group, groupLayout, false);
        RelativeLayout header=(RelativeLayout) group_card.findViewById(R.id.group_header);
        final ImageView arrow=(ImageView) group_card.findViewById(R.id.group_arrow);
        TextView title=(TextView) group_card.findViewById(R.id.group_title);
        final TableLayout table=(TableLayout) group_card.findViewById(R.id.group_table);

        title.setText(paramGroup.Name);
        ViewParams(table, paramGroup.Params);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(table.getVisibility()==View.VISIBLE) {
                    table.setVisibility(View.GONE);
                    arrow.setRotation(90);
                }else {
                    table.setVisibility(View.VISIBLE);
                    arrow.setRotation(0);
                }
            }
        });

        if(bottomSpace) {
            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) group_card.getLayoutParams();
            params2.bottomMargin = Common.dpToPx(this, 50);
            group_card.setLayoutParams(params2);
        }

        groupLayout.addView(group_card);
    }

    private void ViewParams(TableLayout tableLayout, ParamList paramList)
    {
        tableLayout.removeAllViews();
        for(Param param:paramList.Items)
        {
            TableRow row=new TableRow(this);
            TextView text1=new TextView(this);
            TextView text2=new TextView(this);
            text1.setText(param.Name);
            text2.setText(param.Value);
            row.addView(text1);
            row.addView(text2);

            DisplayMetrics displaymetrics = getResources().getDisplayMetrics();

            TableRow.LayoutParams params1 = (TableRow.LayoutParams) text1.getLayoutParams();
            params1.span=1;
            params1.width=displaymetrics.widthPixels;
            text1.setLayoutParams(params1);

            TableRow.LayoutParams params2 = (TableRow.LayoutParams) text2.getLayoutParams();
            params2.span=1;
            params2.width=displaymetrics.widthPixels;
            text2.setLayoutParams(params2);

            row.setPadding(0, Common.dpToPx(this, 5), 0, Common.dpToPx(this, 5));

            tableLayout.addView(row);
        }
    }

    private PageLoaderCallbackInterface updateJournalInterface=new PageLoaderCallbackInterface() {
        @Override
        public void onSuccess(Map<String, Object> resultSet) {
            List<Map<String,String>> list = (List<Map<String,String>>)resultSet.get("events");
            if(list.size()>0) {
                Common.CurrentDeal.Events.Items.clear();
                for (Map<String, String> map3 : list) {
                    Event event = new Event();
                    event.Date = map3.get("dateTime").toString();
                    event.Content = map3.get("eventDescr").toString();
                    Common.CurrentDeal.Events.Items.add(event);
                }
                ViewJournal();
                if(Common.Favorites.IndexOf(Common.CurrentDeal)!=-1)
                    Storage.SaveFavorites(DealDetailsActivity.this);
            }
            journal_progress.setVisibility(View.GONE);
        }

        @Override
        public void onFail(String err) {
            journal_progress.setVisibility(View.GONE);
        }

        @Override
        public void onTimeOut() {
            journal_progress.setVisibility(View.GONE);
        }
    };

    private void ViewJournal()
    {
        journal_table.removeAllViews();
        for(Event event:Common.CurrentDeal.Events.Items)
        {
            TableRow row=new TableRow(this);
            TextView text1=new TextView(this);
            TextView text2=new TextView(this);
            text1.setText(event.Date);
            text2.setText(event.Content);
            row.addView(text1);
            row.addView(text2);

            DisplayMetrics displaymetrics = getResources().getDisplayMetrics();

            TableRow.LayoutParams params1 = (TableRow.LayoutParams) text1.getLayoutParams();
            params1.span=1;
            params1.width=displaymetrics.widthPixels;
            text1.setLayoutParams(params1);

            TableRow.LayoutParams params2 = (TableRow.LayoutParams) text2.getLayoutParams();
            params2.span=1;
            params2.width=displaymetrics.widthPixels;
            text2.setLayoutParams(params2);

            journal_table.addView(row);
        }
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
