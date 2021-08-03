package com.example.mstdnpublic;

import android.os.Message;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * A ViewModel which holds a MutableLiveData instance whose member is Message class.
 * The Message instance contains 2 key-value pairs, they are
 * boolean isValid - indicates whether host is valid or not
 * String host - the host address string
 */
public class HostInputViewModel extends ViewModel {
    private MutableLiveData<Message> message;

    public MutableLiveData<Message> getMessage() {
        if (message == null) {
            message = new MutableLiveData<Message>();
        }
        return message;
    }

}
