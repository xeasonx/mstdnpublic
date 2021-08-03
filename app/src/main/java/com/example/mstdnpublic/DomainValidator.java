package com.example.mstdnpublic;

import android.os.Bundle;
import android.os.Message;
import java.io.IOException;
import java.net.InetAddress;


public class DomainValidator {
    private HostInputViewModel model;

    /**
     *
     * @param model HostInputViewModel
     */
    public DomainValidator(HostInputViewModel model) {
        this.model = model;
    }

    /**
     *
     * Start a thread to check whether host address is valid or not, then send result back through
     * HostInputViewModel instance.
     * @param hostName String, host name to be checked
     */
    public void validate(String hostName) {
        Validator validator = new Validator(hostName, model);
        Thread thread = new Thread(validator);
        thread.start();
    }

    private class Validator implements Runnable {
        private String host;
        private HostInputViewModel model;

        public Validator(String host, HostInputViewModel model) {
            if (host != null) {
                this.host = host.trim();
            } else {
                this.host = "";
            }
            this.model = model;
        }

        @Override
        public void run() {
            Bundle bundle = new Bundle();
            Message message = new Message();
            boolean isValid = false;
            if (!this.host.isEmpty()) {
                try {
                    InetAddress inetAddress = InetAddress.getByName(this.host);
                    if (inetAddress.isReachable(5000)) {
                        isValid = true;
                    }
                } catch (IOException e) {}
            }
            bundle.putString("host", host);
            bundle.putBoolean("isValid", isValid);
            message.setData(bundle);
            model.getMessage().postValue(message);
        }
    }
}
