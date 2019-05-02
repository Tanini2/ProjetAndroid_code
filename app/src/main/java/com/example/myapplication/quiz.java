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
    private static final String Repondu = "repondu";
    private static final String TextChoisiButton = "textChoisiButton";

    int mNbQuestions;
    int mCompteurQuestion = 0;
    int noQuestion;
    int pointage = 0;

    boolean repondu = false;

    String mPseudo;
    String json1;
    String textChoisiButton = "";

    String[][] arrayQuestions;

    ArrayList<Integer> noQuestionShuffle;
    ArrayList<Integer> noButton;

    Question questions;

    TextView mTextQuestion;
    TextView mTextReponse;
    TextView mPointage;

    ImageView mImageQuestion;

    Button mReponse1;
    Button mReponse2;
    Button mReponse3;
    Button mReponse4;
    Button mNextQuestion;

    //Prend les paramètres envoyés par info_quiz et les traitent
    public static Intent newIntent(Context packageContext, String pseudo, int nbQuestions){
        Intent intent = new Intent(packageContext, quiz.class);

        intent.putExtra(EXTRA_PSEUDO, pseudo);
        intent.putExtra(EXTRA_NBQUESTIONS, nbQuestions);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noQuestionShuffle = new ArrayList<>();
        noButton = new ArrayList<>();

        //Si l'écran change de côté ou que l'activité est arrêtée ou mise en pause
        //Pré-rempli le compteur de questions, l'array de questions, le pointage, si la question avait été répondue et le bouton choisi
        if (savedInstanceState != null){
            mCompteurQuestion = savedInstanceState.getInt(CompteurQuestion);
            noQuestionShuffle = savedInstanceState.getIntegerArrayList(NoQuestionShuffle);
            pointage = savedInstanceState.getInt(Pointage);
            repondu= savedInstanceState.getBoolean(Repondu);
            textChoisiButton = savedInstanceState.getString(TextChoisiButton);
        }
        setContentView(R.layout.activity_quiz);

        mNbQuestions = getIntent().getIntExtra(EXTRA_NBQUESTIONS, 0);
        mPseudo = getIntent().getStringExtra(EXTRA_PSEUDO);

        mTextQuestion = (TextView) findViewById(R.id.text_question);
        mTextReponse = (TextView) findViewById(R.id.text_reponse);
        mPointage = (TextView) findViewById(R.id.pointage);
        mPointage.setText(getString(R.string.pointage) + " " + pointage);

        mReponse1 = (Button) findViewById(R.id.buttonReponse1);
        mReponse2 = (Button) findViewById(R.id.buttonReponse2);
        mReponse3 = (Button) findViewById(R.id.buttonReponse3);
        mReponse4 = (Button) findViewById(R.id.buttonReponse4);
        mNextQuestion = (Button) findViewById(R.id.buttonNext);

        mImageQuestion = (ImageView) findViewById(R.id.img_question);

        //Va chercher la liste de questions dans la base de données
        questions = GetJsonQuestions();
        arrayQuestions = questions.GetQuestions();

        //Si l'array de questions n'est pas vide
        if(!noQuestionShuffle.isEmpty()){
            ShuffleButton();
            ChangerQuestion();
        }
        else{
            ShuffleQuestion();
            ShuffleButton();
            ChangerQuestion();
        }



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
                //Si on a atteint la dernière question
                if(mCompteurQuestion == mNbQuestions){
                    //Ouvre l'activité resultat et lui passe en paramètres le pseudo, le nombre de questions et le pointage
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

    //Remet la couleur des boutons à noir lorsqu'on clique sur le bouton Question Suivante
    private void ChangerCouleurBoutonNoir(){
        mReponse1.setTextColor(Color.parseColor("#FFFFFF"));
        mReponse2.setTextColor(Color.parseColor("#FFFFFF"));
        mReponse3.setTextColor(Color.parseColor("#FFFFFF"));
        mReponse4.setTextColor(Color.parseColor("#FFFFFF"));
    }

    //Passe à la prochaine questions et change le texte de tous les TextView et Boutons
    private void ChangerQuestion(){
        repondu = false;
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

    //Active ou désactive la possiblité de cliquer sur un bouton
    //Set la visibilité du TextView contenant la réponse et du bouton Prochaine Question
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

    //Va chercher le texte du bouton choisi et le compare avec le champ du tableau contenant la réponse
    private void GetAnswer(Button mButton){
        textChoisiButton = mButton.getText().toString();
        //Si ce n'est pas la bonne réponse, le texte du bouton devient rouge
        if(mButton.getText() != arrayQuestions[noQuestion - 1][3]){
            mButton.setTextColor(Color.parseColor("#FF0000"));
        }
        //Sinon le bouton devient vert et on ajoute au pointage
        else{
            mButton.setTextColor(Color.parseColor("#7FFF00"));
            pointage++;
            mPointage.setText(getString(R.string.pointage) + " " + pointage);
        }
        repondu = true;
        mTextReponse.setVisibility(View.VISIBLE);
    }

    //On vérifie lequel des 4 boutons contient la bonne réponse, on set les couleurs dépendamment du résultat
    private void GetAnswer(){
        if(mReponse1.getText().toString().equals(textChoisiButton)){
            if(!textChoisiButton.equals(arrayQuestions[noQuestion - 1][3])){
                mReponse1.setTextColor(Color.parseColor("#FF0000"));
            }
            else{
                mReponse1.setTextColor(Color.parseColor("#7FFF00"));
            }
        }
        else if(mReponse2.getText().toString().equals(textChoisiButton)){
            if(!textChoisiButton.equals(arrayQuestions[noQuestion - 1][3])){
                mReponse2.setTextColor(Color.parseColor("#FF0000"));
            }
            else{
                mReponse2.setTextColor(Color.parseColor("#7FFF00"));
            }
        }
        else if(mReponse3.getText().toString().equals(textChoisiButton)){
            if(!textChoisiButton.equals(arrayQuestions[noQuestion - 1][3])){
                mReponse3.setTextColor(Color.parseColor("#FF0000"));
            }
            else{
                mReponse3.setTextColor(Color.parseColor("#7FFF00"));
            }
        }
        else if(mReponse4.getText().toString().equals(textChoisiButton)){
            if(!textChoisiButton.equals(arrayQuestions[noQuestion - 1][3])){
                mReponse4.setTextColor(Color.parseColor("#FF0000"));
            }
            else{
                mReponse4.setTextColor(Color.parseColor("#7FFF00"));
            }
        }
        mTextReponse.setVisibility(View.VISIBLE);
    }

    //À l'aide du PHP et du JSON, obtient la liste des questions disponibles dans la base de données et crée un objet Question
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

    //Mélange les questions et ne prends que le nombre de questions demandé dans info_quiz
    private void ShuffleQuestion(){
        for (int i = 0; i < arrayQuestions.length; i++){
            noQuestionShuffle.add(Integer.parseInt(arrayQuestions[i][0]));
        }
        Collections.shuffle(noQuestionShuffle);
    }

    //Mélange les boutons pour que la réponse ne soit pas toujours au même endroit
    private void ShuffleButton(){
        for (int i = 3; i <= 6; i++){
            noButton.add(i);
        }
        Collections.shuffle(noButton);
    }

    //Si l'écran change de côté ou que l'activité est arrêtée ou mise en pause
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
            repondu= savedInstanceState.getBoolean(Repondu);
            textChoisiButton = savedInstanceState.getString(TextChoisiButton);

            //Si la questions à été répondue, désactive la cliquabilité du bouton et va afficher la réponse
            if(repondu){
                SetClickableButton(false);
                GetAnswer();
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    // Sauvegarde le compteur de questions, l'array de questions, le pointage, si la question a été répondue et le bouton choisi
    // Dans le cas où l'application serait tournée de côté, arrêtée ou mise en pause
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CompteurQuestion, mCompteurQuestion);
        outState.putIntegerArrayList(NoQuestionShuffle, noQuestionShuffle);
        outState.putInt(Pointage, pointage);
        outState.putBoolean(Repondu, repondu);
        outState.putString(TextChoisiButton, textChoisiButton);
        super.onSaveInstanceState(outState);
    }
}


