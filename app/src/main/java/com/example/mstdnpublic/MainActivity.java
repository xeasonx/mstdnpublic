package com.example.mstdnpublic;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;


/**
 * The main activity tries to get local saved host, if not found, HostInputFragment is displayed,
 * waiting user to input an new host server address which serves the mstdn APIs.
 * Otherwise MessageListFragment is used to display tweet message list.
 * The instance variable "TAG" if used for logging through whole app.
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "mstdnpublic";

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String host = sharedPreferences.getString(getString(R.string.preferences_host), "");

        if (host == null || host.isEmpty()) {
            Log.i(TAG, "No host found");
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_main, HostInputFragment.newInstance(TAG))
                    .commit();
        } else {
            Log.i(TAG, String.format("Found saved host %s", host));
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_main, MessageListFragment.newInstance(host))
                    .commit();
        }
    }
}