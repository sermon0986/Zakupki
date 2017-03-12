package com.my.zakupki.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.my.zakupki.Common;
import com.my.zakupki.DataClasses.Deal;
import com.my.zakupki.DataClasses.DealList;
import com.my.zakupki.Net.ExpenseSearchUrlBuilder;
import com.my.zakupki.Net.PageLoaderCallbackInterface;
import com.my.zakupki.Net.RuleTreeFactory;
import com.my.zakupki.Net.UrlstringBuilderFactory;
import com.my.zakupki.Net.WebUrlLoader;
import com.my.zakupki.R;
import com.my.zakupki.Utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FindDealActivity extends AppCompatActivity {

    private Button find_button;
    private MaterialDialog WaitDialog;

    private EditText search_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_deal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_find);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Поиск закупок");

        find_button = (Button)findViewById(R.id.find_button);
        search_string = (EditText) findViewById(R.id.search_string);

        find_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WaitDialog = new MaterialDialog.Builder(FindDealActivity.this)
                        .content("Подождите...")
                        .progress(true, 0)
                        .progressIndeterminateStyle(false)
                        .cancelable(false)
                        .show();


                // when no builder exists, create and tune suitable one:
                if(null == Common.FSearchUrlBuilder){
                    // create via factory:
                    Common.FSearchUrlBuilder = (ExpenseSearchUrlBuilder) UrlstringBuilderFactory.INSTANCE.getUrlstringBuilder("SearchResults"); // can return NULL if the tag is unknown
                    // initialize properly for the first time:
                    if(null != Common.FSearchUrlBuilder) {// get a default parameter set:
                        Map<String, String> params = ExpenseSearchUrlBuilder.getEmptyMap();
                        // put into the search string:
                        params.put("searchString", search_string.getText().toString());
                        Common.FSearchUrlBuilder = Common.FSearchUrlBuilder.setApp(FindDealActivity.this).InitUrl(params);
                    }
                }
                if(null != Common.FSearchUrlBuilder) {
                    // pages total control:
                    // TODO: 28.02.2017 Check what happens when requested page num exceeds pagestotal totally

                    Common.PagesNum=1;

                    Common.FSearchUrlBuilder.setPageNum(Common.PagesNum);
                    WebUrlLoader infogetter = new WebUrlLoader(
                            FindDealActivity.this
                            , callbackInterface
                            , Common.FSearchUrlBuilder.Build()
                            , Common.maClient
                            , RuleTreeFactory.INSTANCE.getTransformer("SearchResults") // can be NULL
                    );
                    Thread tr = new Thread(infogetter);
                    infogetter.setId(tr.getId());
                    tr.start();
//                FLoadTasks.add(new TaskFinishedFlag(infogetter.getId(), false));
                }

            }
        });

    }

    private PageLoaderCallbackInterface callbackInterface=new PageLoaderCallbackInterface() {
        @Override
        public void onSuccess(Map<String, Object> resSet2) {

            if(Common.DealListResults==null)
                Common.DealListResults=new ArrayList<>();

            Common.DealListResults.clear();

            Common.ParseResults(resSet2);

            if(Common.DealListResults.size()>0) {
                startActivity(new Intent(FindDealActivity.this, FoundDealActivity.class));
                if(WaitDialog!=null)
                    WaitDialog.dismiss();
            }else {
                if(WaitDialog!=null)
                    WaitDialog.dismiss();
                Toast.makeText(FindDealActivity.this, "Ничего не найдено", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onFail(String err) {
            //maMainText.append("Fail Message: " + err+"\n");
        }

        @Override
        public void onTimeOut() {
            //maMainText.append("Timeout loading task "+"\n");
        }
    };


}
