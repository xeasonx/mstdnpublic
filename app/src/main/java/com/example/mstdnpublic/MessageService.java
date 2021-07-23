package com.example.mstdnpublic;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.Random;

public class MessageService extends Service {
    private final IBinder binder = new MessageBinder();
    private final Random random = new Random();

    public class MessageBinder extends Binder {
        MessageService getService() {
            Log.i("service", "get service");
            return MessageService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public String[] getRandomNumber() {
        String[] randoms = new String[10];
        for (int i=0; i<10; i++) {
            randoms[i] = String.valueOf(random.nextInt());
        }
        return randoms;
    }
}
