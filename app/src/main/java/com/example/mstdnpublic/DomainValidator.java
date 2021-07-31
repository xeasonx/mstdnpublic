package com.example.mstdnpublic;

import android.os.Handler;
import java.io.IOException;
import java.net.InetAddress;


interface ValidationCallback {
    void onComplete(boolean result);
}

public class DomainValidator {
    private Handler handler;

    public DomainValidator(Handler handler) {
        this.handler = handler;
    }

    public void validate(String hostName, ValidationCallback callback) {
        Validator validator = new Validator(hostName, handler, callback);
        Thread thread = new Thread(validator);
        thread.start();
    }

    private class Validator implements Runnable {
        private String host;
        private Handler handler;
        private ValidationCallback callback;

        public Validator(String host, Handler handler, ValidationCallback callback) {
            this.host = host;
            this.handler = handler;
            this.callback = callback;
        }

        @Override
        public void run() {
            boolean isValid = false;
            if (!this.host.isEmpty()) {
                try {
                    InetAddress inetAddress = InetAddress.getByName(this.host);
                    if (inetAddress.isReachable(5000)) {
                        isValid = true;
                    }
                } catch (IOException e) {}
            }
            boolean finalIsValid = isValid;
            this.handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onComplete(finalIsValid);
                }
            });
        }
    }
}
