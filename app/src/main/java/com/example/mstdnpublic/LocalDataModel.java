package com.example.mstdnpublic;


import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocalDataModel extends ViewModel {
    private final MutableLiveData<String> host = new MutableLiveData<String>();

    public LiveData<String> getHost() {
        Log.i("view model", "host get");
        return this.host;
    }

    public void setHost(String host) {
       this.host.setValue(host);
        Log.i("view model", "host set");
    }
}
