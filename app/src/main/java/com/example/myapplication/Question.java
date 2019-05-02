package com.example.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Question {

    private String[][] questions;

    public Question(String json1) throws JSONException
    {
        JSONArray jsonArray = new JSONArray(json1);
        questions = new String[jsonArray.length()][7];
        //Pour chacune des questions de la jsonArray
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            questions[i][0] = obj.getString("NoQuestion");
            questions[i][1] = obj.getString("Question");
            questions[i][2] = obj.getString("NomImage");
            questions[i][3] = obj.getString("Reponse");
            questions[i][4] = obj.getString("Faux1");
            questions[i][5] = obj.getString("Faux2");
            questions[i][6] = obj.getString("Faux3");
        }
    }

    public String[][] GetQuestions(){
        return questions;
    }
}
