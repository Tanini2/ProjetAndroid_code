package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class quiz extends AppCompatActivity {

    private static final String EXTRA_PSEUDO = "com.example.myapplication.pseudo";
    private static final String EXTRA_NBQUESTIONS = "com.example.myapplication.nbQuestions";
    private static final String NoQuestionShuffle = "noQuestionShuffle";
    private static final String CompteurQuestion = "mCompteurQuestion";
    private static final String Pointage = "pointage";

    int mNbQuestions;
    int mCompteurQuestion;
    int noQuestion;
    int pointage;
    String mPseudo;
    Question questions;
    String json1;
    String[][] arrayQuestions;
    ArrayList<Integer> noQuestionShuffle;
    ArrayList<Integer> noButton;

    Random r;

    TextView mTextQuestion;
    TextView mTextReponse;
    TextView mPointage;

    ImageView mImageQuestion;

    Button mReponse1;
    Button mReponse2;
    Button mReponse3;
    Button mReponse4;
    Button mNextQuestion;

    public static Intent newIntent(Context packageContext, String pseudo, int nbQuestions){
        Intent intent = new Intent(packageContext, quiz.class);

        intent.putExtra(EXTRA_PSEUDO, pseudo);
        intent.putExtra(EXTRA_NBQUESTIONS, nbQuestions);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            mCompteurQuestion = savedInstanceState.getInt(CompteurQuestion);
            noQuestionShuffle = savedInstanceState.getIntegerArrayList(NoQuestionShuffle);
            pointage = savedInstanceState.getInt(Pointage);
            ChangerQuestion();
        }
        setContentView(R.layout.activity_quiz);

        mNbQuestions = getIntent().getIntExtra(EXTRA_NBQUESTIONS, 0);
        mPseudo = getIntent().getStringExtra(EXTRA_PSEUDO);

        mTextQuestion = (TextView) findViewById(R.id.text_question);
        mTextReponse = (TextView) findViewById(R.id.text_reponse);
        mPointage = (TextView) findViewById(R.id.pointage);

        mReponse1 = (Button) findViewById(R.id.buttonReponse1);
        mReponse2 = (Button) findViewById(R.id.buttonReponse2);
        mReponse3 = (Button) findViewById(R.id.buttonReponse3);
        mReponse4 = (Button) findViewById(R.id.buttonReponse4);
        mNextQuestion = (Button) findViewById(R.id.buttonNext);

        mImageQuestion = (ImageView) findViewById(R.id.img_question);

        noQuestionShuffle = new ArrayList<>();
        noButton = new ArrayList<>();

        if(!noQuestionShuffle.isEmpty()){
            noQuestionShuffle.clear();
        }

        for (int i = 3; i <= 6; i++){
            noButton.add(i);
        }
        Collections.shuffle(noButton);
        mCompteurQuestion = 0;
        pointage = 0;

        r = new Random();
        questions = GetJsonQuestions();
        arrayQuestions = questions.GetQuestions();
        ShuffleQuestion();

        ChangerQuestion();

        mReponse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetClickableButton(false);
                GetAnswer(mReponse1);
            }
        });

        mReponse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetClickableButton(false);
                GetAnswer(mReponse2);
            }
        });

        mReponse3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetClickableButton(false);
                GetAnswer(mReponse3);
            }
        });

        mReponse4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetClickableButton(false);
                GetAnswer(mReponse4);
            }
        });

        mNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCompteurQuestion++;
                if(mCompteurQuestion == mNbQuestions){
                    Intent intent = resultat.newIntent(quiz.this, mPseudo, mNbQuestions, pointage);
                    startActivity(intent);
                    return;
                }
                else{
                    ChangerQuestion();
                    SetClickableButton(true);
                    ChangerCouleurBoutonNoir();
                }
            }
        });
    }

    private void ChangerCouleurBoutonNoir(){
        mReponse1.setTextColor(Color.parseColor("#FFFFFF"));
        mReponse2.setTextColor(Color.parseColor("#FFFFFF"));
        mReponse3.setTextColor(Color.parseColor("#FFFFFF"));
        mReponse4.setTextColor(Color.parseColor("#FFFFFF"));
    }

    private void ChangerQuestion(){
        mPointage.setText(getString(R.string.pointage) + " " + pointage);
        noQuestion = noQuestionShuffle.get(mCompteurQuestion);
        mTextQuestion.setText(arrayQuestions[noQuestion - 1][1]);
        mTextReponse.setText(arrayQuestions[noQuestion - 1][3]);
        Picasso.get().load("http://www.dicj.info/etu/vanta1337589/Pokedex/images/Pokemon/" + arrayQuestions[noQuestion - 1][2] + ".png").into(mImageQuestion);
        Collections.shuffle(noButton);
        mReponse1.setText(arrayQuestions[noQuestion - 1][noButton.get(0)]);
        mReponse2.setText(arrayQuestions[noQuestion - 1][noButton.get(1)]);
        mReponse3.setText(arrayQuestions[noQuestion - 1][noButton.get(2)]);
        mReponse4.setText(arrayQuestions[noQuestion - 1][noButton.get(3)]);
    }

    private void SetClickableButton(boolean clickable){
        mReponse1.setClickable(clickable);
        mReponse2.setClickable(clickable);
        mReponse3.setClickable(clickable);
        mReponse4.setClickable(clickable);

        if(clickable){
            mNextQuestion.setVisibility(View.INVISIBLE);
            mTextReponse.setVisibility(View.INVISIBLE);
        }
        else{
            mNextQuestion.setVisibility(View.VISIBLE);
            mTextReponse.setVisibility(View.VISIBLE);
        }

    }

    private void GetAnswer(Button mButton){
        if(mButton.getText() != arrayQuestions[noQuestion - 1][3]){
            mButton.setTextColor(Color.parseColor("#FF0000"));
        }
        else{
            mButton.setTextColor(Color.parseColor("#7FFF00"));
            pointage++;
        }
        mTextReponse.setVisibility(View.VISIBLE);
    }

    private Question GetJsonQuestions(){
        //creating asynctask object and executing it
        Question Ques = null;
        try {
            json1 = new JSON().execute("http://www.dicj.info/etu/vanta1337589/Android/Questions.php").get();
            Ques = new Question(json1);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return Ques;
    }

    private void ShuffleQuestion(){
        for (int i = 0; i < arrayQuestions.length; i++){
            noQuestionShuffle.add(Integer.parseInt(arrayQuestions[i][0]));
        }
        Collections.shuffle(noQuestionShuffle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i("Pokedex", "onRestoreInstanceState");
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (savedInstanceState != null){
            mCompteurQuestion = savedInstanceState.getInt(CompteurQuestion);
            noQuestionShuffle = savedInstanceState.getIntegerArrayList(NoQuestionShuffle);
            pointage = savedInstanceState.getInt(Pointage);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CompteurQuestion, mCompteurQuestion);
        outState.putIntegerArrayList(NoQuestionShuffle, noQuestionShuffle);
        outState.putInt(Pointage, pointage);
        super.onSaveInstanceState(outState);
    }
}


