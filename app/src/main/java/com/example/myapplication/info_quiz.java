package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class info_quiz extends AppCompatActivity {

    NumberPicker mNumberPicker;
    EditText mEditText;
    String mPseudo;
    int mNbQuestions;
    Button mLetsGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_quiz);

        mNumberPicker = (NumberPicker) findViewById(R.id.numberpicker_nbquestions);
        mEditText = (EditText) findViewById(R.id.edittext_pseudo);
        mLetsGo = (Button) findViewById(R.id.button_letsgo);

        mNumberPicker.setMinValue(1);
        mNumberPicker.setMaxValue(50);

        mLetsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mEditText.getText().toString().isEmpty()){
                    mPseudo = mEditText.getText().toString();
                }
                else{
                    mPseudo = "Guest";
                }

                mNbQuestions = mNumberPicker.getValue();

                Intent intent = quiz.newIntent(info_quiz.this, mPseudo, mNbQuestions);
                startActivity(intent);
            }
        });
    }
}
