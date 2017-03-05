package com.my.zakupki.Net;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.util.Map;

/**
 * Created by Dariya on 18.02.2017.
 */

public abstract class BaseRunnableResourseLoader implements Runnable{

    private Activity FActivity; // Activity , which ui thread is used to run callback on
    private PageLoaderCallbackInterface FCallbackInterface; // the callback

//    public abstract class LoadResultConstants {
//        public static final int LOAD_SUCCESS = 0;
//        public static final int LOAD_FAIL = 1;
//        public static final int LOAD_TIMEOUT = 2;
//    }

    long FTaskId;

    public long getId(){
        return FTaskId;
    }
    public void setId(long aTaskId){
        FTaskId = aTaskId;
    }
    public BaseRunnableResourseLoader(Activity uiActivity, PageLoaderCallbackInterface callbackInterface){
        FActivity = uiActivity;
        FCallbackInterface = callbackInterface;
    }
    Activity getUiActivity(){
        return FActivity;
    }
    PageLoaderCallbackInterface getCallbackInterface(){
        return FCallbackInterface;
    }

//    Handler FHandle; // we send messages to it

//    public BaseRunnableResourseLoader(Handler aHandle){
//        FHandle = aHandle;
//    }
//    public BaseRunnableResourseLoader(Handler aHandle, long aTaskId){
//        FHandle = aHandle;
//        setId(aTaskId);
//    }

    public void SendSuccesResult(final Map<String, Object> aResultSet){
//        Message msgForHandler = FHandle.obtainMessage(BaseRunnableResourseLoader.LoadResultConstants.LOAD_SUCCESS, (int)this.getId(), 0, aResult);
//        FHandle.sendMessage(msgForHandler);
        if(null != getUiActivity())
            getUiActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CallSuccess(aResultSet);
                }
            });
        else CallSuccess(aResultSet);
    }
    public void SendFailMessage(final String aFailMessage){
//        Message msgForHandler = FHandle.obtainMessage(BaseRunnableResourseLoader.LoadResultConstants.LOAD_FAIL, (int)this.getId(), 0, aFailMessage);
//        FHandle.sendMessage(msgForHandler);
        if(null != getUiActivity())
            getUiActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CallFail(aFailMessage);
                }
            });
        else CallFail(aFailMessage);
    }
//    public void SendTimeoutEvent(){
//        Message msgForHandler = FHandle.obtainMessage(BaseRunnableResourseLoader.LoadResultConstants.LOAD_TIMEOUT, (int)this.getId(), 0);
//        FHandle.sendMessage(msgForHandler);
//    }
    private void CallSuccess(final Map<String, Object> aResultSet){
        if(null != getCallbackInterface())
            getCallbackInterface().onSuccess(aResultSet);
    }

    private void CallFail(final String aFailMessage){
        if(null != getCallbackInterface())
            getCallbackInterface().onFail(aFailMessage);
    }
}
