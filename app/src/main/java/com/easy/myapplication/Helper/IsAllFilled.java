package com.easy.myapplication.Helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RelativeLayout;


import com.easy.myapplication.Interface.AllInterface;

import java.util.ArrayList;
import java.util.List;


public class IsAllFilled {
    List<EditText> editTexts = new ArrayList<>();

    public IsAllFilled(List<EditText> editTexts) {
        this.editTexts = editTexts;
    }
    public void onFilled(AllInterface allInterface){
        for (EditText edt:editTexts){
            edt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    for (EditText edt:editTexts){
                        if (edt.getText().toString().trim().length()<=0){
                            allInterface.isClicked(false);
                            return;
                        }
                    }
                    allInterface.isClicked(true);

                }
            });

        }
    }
}
