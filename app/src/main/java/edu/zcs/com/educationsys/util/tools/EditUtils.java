package edu.zcs.com.educationsys.util.tools;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 输入框判断（edittext,所需的正则表达式，Toast提示内容）
 */
public class EditUtils {

    public static final String PHONE="(86)*0*13\\d{9}";
    public static final String ACCOUNT="[a-zA-Z\\\\d]*";

    public void set(final EditText et, final String regular, final String msg) {
            et.addTextChangedListener(new TextWatcher() {
                String before = "";
                @Override
                public void onTextChanged(CharSequence s, int start, int before,int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,int after) {
                    before = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().matches(regular) && !"".equals(s.toString())) {
                        et.setText(before);
                        et.setSelection(et.getText().toString().length());
                        if (msg != null) {
                            Toast.makeText(et.getContext(), msg, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }
            });
        }
}
