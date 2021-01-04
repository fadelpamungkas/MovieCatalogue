package com.del.moviecatalogue2.services;

import android.app.IntentService;
import android.content.Intent;

import com.del.moviecatalogue2.ui.MainActivity;

public class DownloadService extends IntentService {

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Intent notify = new Intent(MainActivity.STATUS);
            sendBroadcast(notify);
        }
    }
}
