package com.example.mstdnpublic;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Controls widgets' interaction logic inside HostInputFragment
 */
public class HostInputController {
    private final View view;
    private final String TAG;
    private final DomainValidator domainValidator;

    public HostInputController(View view, HostInputViewModel model, String TAG) {
        this.view = view;
        this.domainValidator = new DomainValidator(model);
        this.TAG = TAG;
    }

    /**
     * Set Button and EditText widgets' interaction logic.
     */
    public void control() {
        EditText editText = (EditText) view.findViewById(R.id.edit_host);
        Button button = (Button) view.findViewById(R.id.confirm_host);
        CustomTextWatcher textWatcher = new CustomTextWatcher();
        editText.addTextChangedListener(textWatcher);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    domainValidator.validate(textWatcher.getHost());
                    return true;
                }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                domainValidator.validate(textWatcher.getHost());
            }
        });

    }

    private static class CustomTextWatcher implements TextWatcher {
        private String host;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            host = s.toString();
        }

        public String getHost() {
            return host;
        }
    }

}
