package com.my.zakupki.Net;

import android.app.Activity;
import android.os.Handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Dariya on 18.02.2017.
 */

public class WebUrlLoader extends BaseRunnableResourseLoader {
    String FUri;
    String FAnswer;
    private OkHttpClient FClient;
    private BaseTreeResultTransformer FTransformer;

    public WebUrlLoader(Activity activity, PageLoaderCallbackInterface callbackInterface, String aUri, OkHttpClient aClient, BaseTreeResultTransformer aTransformer){
        super(activity, callbackInterface);
        FUri = aUri;
        FClient = aClient;
        FTransformer = aTransformer;
    }

    @Override
    public void run(){
        Request request = new Request.Builder()
                .url(FUri)
                .build();
        try{
            Response response = FClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                FAnswer = "Fail[" + "]: " + response;
                response.close();
                SendFailMessage(FAnswer);
            }
            else {
                FAnswer = response.body().string(); // this closes the stream
                if(null != FTransformer) {
                    Map<String, Object> resultSet = FTransformer.setSourceData(FAnswer).Transform();
                    this.SendSuccesResult(resultSet);
                }
                else
                    this.SendFailMessage("No Result transformer for the result ["+getId()+"](\n"+FAnswer+"\n)");
            }
        } catch(IOException ex){
            FAnswer = "Fail ["+getId()+"](\n"+ex.toString()+"\n)";
            this.SendFailMessage(FAnswer);
        }
    }
}

