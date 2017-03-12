package com.my.zakupki.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.my.zakupki.Adapters.RecyclerViewAdapter_Found;
import com.my.zakupki.Adapters.RecyclerViewAdapter_Main;
import com.my.zakupki.Common;
import com.my.zakupki.DataClasses.Deal;
import com.my.zakupki.Interfaces.AdapterInterface;
import com.my.zakupki.Net.PageLoaderCallbackInterface;
import com.my.zakupki.Net.RuleTreeFactory;
import com.my.zakupki.Net.WebUrlLoader;
import com.my.zakupki.R;

import java.util.Map;

public class FoundDealActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter_Found recyclerViewAdapter;
    private LinearLayoutManager layoutManager;

    private boolean loading = false;
    private boolean NoMore;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_deal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_found);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Результаты поиска");

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);

        recyclerViewAdapter = new RecyclerViewAdapter_Found(Common.DealListResults, adapterInterface);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                if (!loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        if (!NoMore) {

                            RelativeLayout loading_layout=(RelativeLayout)findViewById(R.id.loading_progress);
                            loading_layout.setVisibility(View.VISIBLE);

                            if(Common.PagesNum < Common.PagesTotal)
                                Common.PagesNum++;
                            else
                                Common.PagesNum = 1;

                            Common.FSearchUrlBuilder.setPageNum(Common.PagesNum);
                            WebUrlLoader infogetter = new WebUrlLoader(
                                    FoundDealActivity.this
                                    , callbackInterface
                                    , Common.FSearchUrlBuilder.Build()
                                    , Common.maClient
                                    , RuleTreeFactory.INSTANCE.getTransformer("SearchResults") // can be NULL
                            );
                            Thread tr = new Thread(infogetter);
                            infogetter.setId(tr.getId());
                            tr.start();
                        }
                    }
                }
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        recyclerViewAdapter.notifyDataSetChanged();

    }

    private PageLoaderCallbackInterface callbackInterface=new PageLoaderCallbackInterface() {
        @Override
        public void onSuccess(Map<String, Object> resultSet) {

            RelativeLayout loading_layout=(RelativeLayout)findViewById(R.id.loading_progress);
            loading_layout.setVisibility(View.GONE);

            loading=false;

            Common.ParseResults(resultSet);

            recyclerViewAdapter = new RecyclerViewAdapter_Found(Common.DealListResults, adapterInterface);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.scrollToPosition(pastVisiblesItems);
        }

        @Override
        public void onFail(String err) {

        }

        @Override
        public void onTimeOut() {

        }
    };

    private AdapterInterface adapterInterface=new AdapterInterface() {
        @Override
        public void onItemClick(View v) {
            Common.CurrentDeal=(Deal)v.getTag();
            startActivity(new Intent(FoundDealActivity.this, DealDetailsActivity.class));
        }

        @Override
        public boolean onItemLongClick(View v) {
            return false;
        }
    };



}
