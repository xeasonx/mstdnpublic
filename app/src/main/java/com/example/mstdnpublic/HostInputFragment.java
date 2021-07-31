package com.example.mstdnpublic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;


public class HostInputFragment extends Fragment {
    private EditText editText;
    private Button button;
    private String host = "";
    private Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
    private DomainValidator domainValidator;

    public HostInputFragment() {
        super(R.layout.fragment_input_host);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input_host, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        editText = (EditText) getView().findViewById(R.id.edit_host);
        button = (Button) getView().findViewById(R.id.confirm_host);
        CustomTextWatcher textWatcher = new CustomTextWatcher();
        editText.addTextChangedListener(textWatcher);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    host = textWatcher.getHost().trim();
                    domainValidator = new DomainValidator(handler);
                    domainValidator.validate(host, new ValidationCallback() {
                        @Override
                        public void onComplete(boolean result) {
                            Toast.makeText(getActivity(), String.valueOf(result), Toast.LENGTH_SHORT).show();
                        }
                    });
                    return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textWatcher.getHost() == null) {
                    host = "";
                } else {
                    host = textWatcher.getHost().trim();
                }
                domainValidator = new DomainValidator(handler);
                domainValidator.validate(host, new ValidationCallback() {
                    @Override
                    public void onComplete(boolean result) {
                        if (result) {
                            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(getString(R.string.preferences_host), host);
                            editor.apply();
                            Log.i("domain validator", "domain saved");
                            getParentFragmentManager()
                                .beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragment_input_host, MessageListFragment.newInstance(host))
                                .commit();
                        } else {
                            Toast.makeText(getActivity(), "invalid domain", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Log.i("click", host);
            }
        });
    }

    public class CustomTextWatcher implements TextWatcher {
        private String host;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            host = s.toString();
        }

        public String getHost() {
            return host;
        }
    }
}