package com.my.zakupki.Jobs;


import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.my.zakupki.Common;
import com.my.zakupki.DataClasses.Deal;
import com.my.zakupki.DataClasses.Event;
import com.my.zakupki.Net.PageLoaderCallbackInterface;
import com.my.zakupki.NotificationHelper;
import com.my.zakupki.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UpdateJob extends Job {

    public static final String TAG = "job_update_journal";

    //private Context

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        // run your job here

        if(Common.Favorites==null)
            Storage.LoadFavorites(getContext());


//        Common.CurrentDeal=Common.Favorites.Items.get(0);
//        //start update
//        UniZhournalUrlBuilder uniZhournalUrlBuilder=new UniZhournalUrlBuilder();
//        if(!Common.CurrentDeal.UrlCommonInfo.isEmpty()) {
//            uniZhournalUrlBuilder.InitUrl(Common.CurrentDeal.UrlCommonInfo);
//            WebUrlLoader journalgetter = new WebUrlLoader(
//                    (Activity)getContext()
//                    , updateJournalInterface
//                    , uniZhournalUrlBuilder.Build()
//                    , Common.maClient
//                    , RuleTreeFactory.INSTANCE.getTransformer("Uni_Zhournal")
//            );
//            Thread tr = new Thread(journalgetter);
//            journalgetter.setId(tr.getId());
//            tr.start();
//        }


        return Result.SUCCESS;
    }

    private void OnCompleteJob()
    {
        long LastNotificationTime=Long.valueOf(Storage.ReadString(getContext(), "LastNotificationTime", String.valueOf(System.currentTimeMillis()-60000)));

        List<Event> ForNotify=new ArrayList<>();
        for(Deal deal:Common.Favorites.Items)
            for(Event event:deal.Events.Items)
                if(!event.Showed && event.UpdateTime>LastNotificationTime)
                    ForNotify.add(event);

        for(int i=0;i<ForNotify.size();i++)
            NotificationHelper.SendNotification(getContext(), "New event", ForNotify.get(i).Content, ForNotify.get(i).Date+"\t"+ForNotify.get(i).Content, 0, true);

        if(ForNotify.size()>0)
            Storage.SaveString(getContext(), "LastNotificationTime", String.valueOf(System.currentTimeMillis()));
    }

    private PageLoaderCallbackInterface updateJournalInterface=new PageLoaderCallbackInterface() {
        @Override
        public void onSuccess(Map<String, Object> resultSet) {
            List<Map<String,String>> list = (List<Map<String,String>>)resultSet.get("events");
            if(list.size()>0) {
                for (Map<String, String> map3 : list) {
                    Event event = new Event();
                    event.Date = map3.get("dateTime").toString();
                    event.Content = map3.get("eventDescr").toString();
                    event.UpdateTime = System.currentTimeMillis();
                    if(Common.CurrentDeal.Events.IndexOf(event)==-1) {
                        Common.CurrentDeal.Events.Items.add(0, event);
                    }
                }
                //test new event
//                Event event = new Event();
//                event.Date = "2017.04.20";
//                event.Content = "Content";
//                event.UpdateTime = System.currentTimeMillis();
//                Common.CurrentDeal.Events.Items.add(0, event);
            }
            OnCompleteJob();
        }
        @Override
        public void onFail(String err) {
        }
        @Override
        public void onTimeOut() {
        }
    };

    public static void scheduleJob() {
        new JobRequest.Builder(UpdateJob.TAG)
                .setExecutionWindow(3_000L, 4_000L)
                .setBackoffCriteria(5_000L, JobRequest.BackoffPolicy.LINEAR)
                .setRequirementsEnforced(true)
                .setPersisted(true)
                .build()
                .schedule();
    }

    public static void schedulePeriodicJob() {
        int jobId = new JobRequest.Builder(UpdateJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                .setPersisted(true)
                .build()
                .schedule();
    }
}
