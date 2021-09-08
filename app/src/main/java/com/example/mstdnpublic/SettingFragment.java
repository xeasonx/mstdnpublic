package com.example.mstdnpublic;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreference;

public class SettingFragment extends PreferenceFragmentCompat {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.app_settings).setVisible(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.app_prefrences, rootKey);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.confirm_settings, menu);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();

        switch (item.getItemId()) {
            case R.id.save_settings:
                SeekBarPreference seekBarPreference = findPreference("maxTweets");
                SwitchPreference switchPreference = findPreference("autoRefresh");
                editor.putInt("maxTweets", seekBarPreference.getValue());
                editor.putBoolean("autoRefresh", switchPreference.isEnabled());
                editor.apply();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}