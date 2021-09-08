package com.example.mstdnpublic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


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
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String host = sharedPreferences.getString(getString(R.string.preferences_host), "");
        int maxTweets = sharedPreferences.getInt("maxTweets", 20);
        boolean autoRefresh = sharedPreferences.getBoolean("autoRefresh", false);
        Log.i(TAG, String.format("preferences: %s, %d, %b%n", host, maxTweets, autoRefresh));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_settings:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_main, new SettingFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}