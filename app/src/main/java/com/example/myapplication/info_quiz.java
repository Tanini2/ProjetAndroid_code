package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class info_quiz extends AppCompatActivity {

    private NumberPicker mNumberPicker;

    private EditText mEditText;

    private String mPseudo;

    private int mNbQuestions;

    private Button mLetsGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_quiz);

        mNumberPicker = (NumberPicker) findViewById(R.id.numberpicker_nbquestions);
        mEditText = (EditText) findViewById(R.id.edittext_pseudo);
        mLetsGo = (Button) findViewById(R.id.button_letsgo);

        //Les valeurs du NumberPicker sont entre 1 et 50
        mNumberPicker.setMinValue(1);
        mNumberPicker.setMaxValue(50);

        mLetsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Conserve le pseudonyme entré si le champ n'est pas vide
                if(!mEditText.getText().toString().isEmpty()){
                    mPseudo = mEditText.getText().toString();
                }
                //Sinon garde la String par défaut
                else{
                    mPseudo = getString(R.string.no_name);
                }

                mNbQuestions = mNumberPicker.getValue();

                //Ouvre l'activité quiz en lui passant le pseudo et le nombre de questions choisies
                Intent intent = quiz.newIntent(info_quiz.this, mPseudo, mNbQuestions);
                startActivity(intent);
            }
        });
    }
}
