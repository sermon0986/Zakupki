package com.my.zakupki;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.my.zakupki.Jobs.UpdateJobCreator;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new UpdateJobCreator());
    }
}