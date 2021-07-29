package com.example.mstdnpublic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ServiceTestRule;

import com.example.mstdnApi.MSTDNRestfulRegister;
import com.example.mstdnResponseEntities.Error;
import com.example.mstdnResponseEntities.Status;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public final ServiceTestRule serviceTestRule = new ServiceTestRule();

    @Test
    public void testWithBoundService() throws TimeoutException {
        Looper.prepare();
        Intent serviceIntent = new Intent(ApplicationProvider.getApplicationContext(), MSTDNService.class);
        IBinder binder = serviceTestRule.bindService(serviceIntent);
        MSTDNService.MSTDNRestfulBinder mstdnRestfulBinder = (MSTDNService.MSTDNRestfulBinder) binder;
        MSTDNService service = mstdnRestfulBinder.getService();
        service.setHandler(new TempHandler());
        service.callApi("TimelinePublic");
        Looper.loop();
    }

    public class TempHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int statusCode = bundle.getInt("statusCode");
            boolean isJson = bundle.getBoolean("isJson");
            String body = bundle.getString("body");
            ArrayList<String> objectMembers = new ArrayList<>(Arrays.asList("account", "media_attachments", "application", "mentions", "tags", "emojis", "poll", "relog", "card"));
            Status[] statuses = null;
            Error error = null;
            System.out.println(String.valueOf(statusCode));
            System.out.println(String.valueOf(isJson));
            System.out.println(body);
            if (statusCode == 200 && isJson) {
                Gson gson = new Gson();
                try {
                    statuses = gson.fromJson(body, Status[].class);
                } catch (Exception e) {
                    e.printStackTrace();
                    error = gson.fromJson(body, Error.class);
                }
                if (statuses != null) {
                    Field[] fields = statuses[0].getClass().getFields();
                    assertEquals(20, statuses.length);
                    try {
                        for (Field field : fields) {
                            String key = field.getName();
                            System.out.println(key);
                            System.out.println(statuses[0].bookmarked);
                            if (!objectMembers.contains(key)) {
                                Object value = field.get(statuses[0].getClass().newInstance());
                                assertNotNull(value);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        fail("error accessing Status class members");
                    }

                } else {
                    assertNotNull(error.error);
                    assertNotNull(error.error_description);
                }
            } else {
                fail("http request error");
            }
            Looper.myLooper().quitSafely();
        }
    }
}