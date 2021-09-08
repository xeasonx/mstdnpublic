package com.example.mstdnpublic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;


/**
 * Fragment accepting user to input host address
 */
public class HostInputFragment extends Fragment {
    private String TAG;
    private HostInputViewModel model;

    /**
     * Default constructor
     */
    public HostInputFragment() {
        super(R.layout.fragment_main);
    }

    /**
     *
     * @param tag String, logging tag, the first argument of "Log".
     * @return A new HostInputFragment instance.
     */
    public static HostInputFragment newInstance(String tag) {
        HostInputFragment fragment = new HostInputFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = getArguments().getString("tag");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        model = new ViewModelProvider(this).get(HostInputViewModel.class);
        HostInputController controller = new HostInputController(getView(), model, TAG);
        controller.control();
        final Observer<Message> observer = new Observer<Message>() {
            @Override
            public void onChanged(Message message) {
                Bundle bundle = message.getData();
                if (bundle != null) {
                    boolean isValid = bundle.getBoolean("isValid");
                    String host = bundle.getString("host");
                    if (isValid) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                        inputMethodManager.showSoftInput(getView(), InputMethodManager.SHOW_IMPLICIT);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(getString(R.string.preferences_host), host);
                        editor.apply();
                        Log.i(TAG, "domain saved");
                        getParentFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_main, MessageListFragment.newInstance(host))
                            .commit();
                    } else {
                        Toast.makeText(getContext(), R.string.status_validate_fail, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        model.getMessage().observe(this, observer);
    }
}