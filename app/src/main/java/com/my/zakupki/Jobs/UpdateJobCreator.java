package com.my.zakupki.Jobs;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class UpdateJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            case UpdateJob.TAG:
                return new UpdateJob();
            default:
                return null;
        }
    }
}
