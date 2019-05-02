package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class resultat extends AppCompatActivity {

    private static final String EXTRA_PSEUDO = "com.example.myapplication.pseudo";
    private static final String EXTRA_NBQUESTIONS = "com.example.myapplication.nbQuestions";
    private static final String EXTRA_POINTAGE = "com.example.myapplication.pointage";

    private int nbQuestions;
    private int pointage;

    private String pseudo;

    Button mButtonRetourAccueil;
    Button mButtonRejouer;

    TextView mPseudo;
    TextView mScore;
    TextView mTextResult;

    //Reçoit les paramètres de quiz et les traitent
    public static Intent newIntent(Context packageContext, String pseudo, int nbQuestions, int pointage){
        Intent intent = new Intent(packageContext, resultat.class);

        intent.putExtra(EXTRA_PSEUDO, pseudo);
        intent.putExtra(EXTRA_NBQUESTIONS, nbQuestions);
        intent.putExtra(EXTRA_POINTAGE, pointage);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);

        nbQuestions = getIntent().getIntExtra(EXTRA_NBQUESTIONS, 0);
        pseudo = getIntent().getStringExtra(EXTRA_PSEUDO);
        pointage = getIntent().getIntExtra(EXTRA_POINTAGE, 0);

        mButtonRetourAccueil = (Button) findViewById(R.id.buttonRetour);
        mButtonRetourAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ouvre l'activité MainActivity
                Intent intent = new Intent(resultat.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mButtonRejouer = (Button) findViewById(R.id.buttonRejouer);
        mButtonRejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ouvre l'activité info_quiz
                Intent intent = new Intent(resultat.this, info_quiz.class);
                startActivity(intent);
            }
        });

        mPseudo = (TextView) findViewById(R.id.text_pseudo);
        mPseudo.setText(pseudo);

        mScore = (TextView) findViewById(R.id.text_score);
        mScore.setText(pointage + " / " + nbQuestions);

        mTextResult = (TextView) findViewById(R.id.text_phrase_resultat);

        //Calcule le score en pourcentage à partir du pointage et du nombre de questions répondues
        //Set le texte dépendamment du score obtenu
        double score = ((double)pointage/(double)nbQuestions)*100;
        if(score < 10){
            mTextResult.setText(getString(R.string.score_pourcentage10));
        }
        else if(score < 20 && score >= 10){
            mTextResult.setText(getString(R.string.score_pourcentage20));
        }
        else if(score < 30 && score >= 20){
            mTextResult.setText(getString(R.string.score_pourcentage30));
        }
        else if(score < 40 && score >= 30){
            mTextResult.setText(getString(R.string.score_pourcentage40));
        }
        else if(score < 50 && score >= 40){
            mTextResult.setText(getString(R.string.score_pourcentage50));
        }
        else if(score < 60 && score >= 50){
            mTextResult.setText(getString(R.string.score_pourcentage60));
        }
        else if(score < 70 && score >= 60){
            mTextResult.setText(getString(R.string.score_pourcentage70));
        }
        else if(score < 80 && score >= 70){
            mTextResult.setText(getString(R.string.score_pourcentage80));
        }
        else if(score < 90 && score >= 80){
            mTextResult.setText(getString(R.string.score_pourcentage90));
        }
        else if(score < 100 && score >= 90){
            mTextResult.setText(getString(R.string.score_pourcentage100));
        }
        else if(score == 100){
            mTextResult.setText(getString(R.string.score_pourcentage_pile_100));
        }
    }

    //Si l'utilisateur appuie sur le bouton Retour de son téléphone, ouvre l'activité MainActivity
    //Empêche de retourner dans le quiz et de changer ses réponses
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(resultat.this, MainActivity.class);
        startActivity(intent);
    }
}
