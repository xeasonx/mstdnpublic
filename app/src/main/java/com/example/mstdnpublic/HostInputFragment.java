package com.example.mstdnpublic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


public class HostInputFragment extends Fragment {
    private EditText editText;
    private Button button;
    private String host = "";
    private LocalDataModel dataModel;

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
        dataModel = new ViewModelProvider(requireActivity()).get(LocalDataModel.class);
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
                    boolean isHostChecked = checkHost();
                    if (isHostChecked) {
                        button.callOnClick();
                    }
                    return isHostChecked;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                host = textWatcher.getHost().trim();
                boolean isHostChecked = checkHost();
                if (isHostChecked) {
//                    dataModel.setHost(host);
                    SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(getString(R.string.preferences_host), host);
                    editor.apply();
                    getParentFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_input_host, MessageListFragment.class, null).commit();
                    Log.i("click", host);
                }
            }
        });
    }

    public boolean checkHost() {
        if (this.host.isEmpty()) {
            Toast.makeText(getActivity(), "invalid host", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
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
