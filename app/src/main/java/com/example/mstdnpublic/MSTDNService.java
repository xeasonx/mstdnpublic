package com.example.mstdnpublic;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.example.mstdnApi.MSTDNRestfulCallback;
import com.example.mstdnApi.MSTDNRestfulRegister;
import com.google.gson.Gson;
import org.chromium.net.CronetEngine;
import org.chromium.net.UrlRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.Executors;


public class MSTDNService extends Service {
    private final IBinder binder = new MSTDNRestfulBinder();
    private CronetEngine cronetEngine;
    private MSTDNRestfulRegister[] apiRegister;
    private boolean isApiRegistered;
    private InputStream inputStream = null;
    private BufferedReader bufferedReader = null;
    private Random random = new Random();
    private Handler handler;


    public class MSTDNRestfulBinder extends Binder {
        MSTDNService getService() {
            return MSTDNService.this;
        }
    }

    private void setupCronetEngine() {
        CronetEngine.Builder builder = new CronetEngine.Builder(this);
        cronetEngine = builder.build();

    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void getApiRegister() {
        Gson gson = new Gson();
        try {
            inputStream = getBaseContext().getAssets().open("mstdnApiRegistry.json");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            String jsonStr;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            jsonStr = stringBuilder.toString();
            Log.d("JSON", jsonStr);
            apiRegister = gson.fromJson(jsonStr, MSTDNRestfulRegister[].class);
            isApiRegistered = true;
        } catch (IOException e) {
            isApiRegistered = false;
        }

        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {}
        }

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {}
        }
    }

    public MSTDNRestfulRegister getApiRegister(String name) {
        if (apiRegister != null) {
            for (MSTDNRestfulRegister reg : apiRegister) {
                if (reg.name.equals(name)) {
                    return reg;
                }
            }
        }
        return null;
    }

    public IBinder onBind(Intent intent) {
        setupCronetEngine();
        getApiRegister();
        return binder;
    }

    public void callApi(String apiName) {
        MSTDNRestfulRegister reg = getApiRegister(apiName);
        if (reg != null) {
            UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                    reg.path,
                    new MSTDNRestfulCallback(handler),
                    Executors.newSingleThreadExecutor()
            );
            requestBuilder.setHttpMethod(reg.method);
            if (reg.headers.length > 0) {
                for (MSTDNRestfulRegister.Header header : reg.headers) {
                    requestBuilder.addHeader(header.key, header.value);
                }
            }

            UrlRequest request = requestBuilder.build();
            request.start();
        } else {
            Log.i("request", "api not found!!");
        }
    }

    public String[] getRandomNumber() {
        String[] randoms = new String[10];
        for (int i=0; i<10; i++) {
            randoms[i] = String.valueOf(random.nextInt());
        }
        return randoms;
    }
}
