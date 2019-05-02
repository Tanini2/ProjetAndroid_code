package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mPokedex;
    private Button mQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPokedex = (Button) findViewById(R.id.button_pokedex);
        mQuiz = (Button) findViewById(R.id.button_quiz);

        //Ouvre l'activité pokemon_vedettes
        mPokedex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, pokemon_vedettes.class);
                startActivity(intent);
            }
        });

        //Ouvre l'activité info_quiz
        mQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, info_quiz.class);
                startActivity(intent);
            }
        });
    }
}
