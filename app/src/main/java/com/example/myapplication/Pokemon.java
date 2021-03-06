package com.example.myapplication;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pokemon {

    private String[][] infosPokemon;
    private String[][] evolution;
    private String[][] megaEvolution;
    private String[][] pokedexEntries;

    //Constructeur pour fiche_pokemon
    public Pokemon(String json1, String json2, String json3, String json4)throws JSONException{

        //Renferme les informations de base du pokémon (# Génération, # Pokémon, Nom Pokémon et ses types)
        JSONArray jsonArray = new JSONArray(json1);
        infosPokemon = new String[jsonArray.length()][5];
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            infosPokemon[i][0] = obj.getString("NoGeneration");
            infosPokemon[i][1] = obj.getString("NoPokemon");
            infosPokemon[i][2] = obj.getString("NomPokemon");
            infosPokemon[i][3] = obj.getString("Type1");
            infosPokemon[i][4] = obj.getString("Type2");
        }

        //Renferme les informations relatives à l'évolution du Pokémon (# Pokémon Évolué, son nom, son type d'évolution et sa description
        JSONArray jsonArray2 = new JSONArray(json2);
        if(jsonArray2 != null && jsonArray2.length() > 0){
            evolution = new String[jsonArray2.length()][4];
            for(int i = 0; i < jsonArray2.length(); i++){
                JSONObject obj2 = jsonArray2.getJSONObject(i);
                evolution[i][0] = obj2.getString("NoPokemonE");
                evolution[i][1]  = obj2.getString("NomPokemonE");
                evolution[i][2]  = obj2.getString("TypeEvolution");
                evolution[i][3]  = obj2.getString("Description");
            }
        }
        //Si la string est vide, on retourne le tableau de string contenant une seule entrée : ""
        else{
            evolution = new String[1][1];
            evolution[0][0] = "";
        }

        //Renferme les informations concernant la méga-évolution (les types et la pierre de Méga-Évolution nécessaire)
        JSONArray jsonArray3 = new JSONArray(json3);
        if(jsonArray3 != null && jsonArray3.length() > 0){
            megaEvolution = new String[jsonArray3.length()][3];
            for(int i = 0; i < jsonArray3.length(); i++){
                JSONObject obj3 = jsonArray3.getJSONObject(i);
                megaEvolution[i][0] = obj3.getString("Type1");
                megaEvolution[i][1] = obj3.getString("Type2");
                megaEvolution[i][2] = obj3.getString("PierreMegaEvolution");
            }
        }
        //Si la string est vide, on retourne le tableau de string contenant une seule entrée : ""
        else{
            megaEvolution = new String[1][1];
            megaEvolution[0][0] = "";
        }

        //Renferme les informations concernant les entrées du Pokédex (Nom du jeu, l'entrée Pokédex et le # Génération
        JSONArray jsonArray4 = new JSONArray(json4);
        pokedexEntries = new String[jsonArray4.length()][3];
        for(int i = 0; i < jsonArray4.length(); i++){
            JSONObject obj4 = jsonArray4.getJSONObject(i);
            pokedexEntries[i][0] = obj4.getString("NomJeu");
            pokedexEntries[i][1] = obj4.getString("PokedexEntry");
            pokedexEntries[i][2] = obj4.getString("NoGeneration");
        }
    }

    //Constructeur pour liste_pokemon
    public Pokemon(String json1) throws JSONException{
        //Renferme les informations de base du pokémon (# Génération, # Pokémon, Nom Pokémon et ses types)
        JSONArray jsonArray = new JSONArray(json1);
        infosPokemon = new String[jsonArray.length()][5];
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            infosPokemon[i][0] = obj.getString("NoGeneration");
            infosPokemon[i][1] = obj.getString("NoPokemon");
            infosPokemon[i][2] = obj.getString("NomPokemon");
            infosPokemon[i][3] = obj.getString("Type1");
            infosPokemon[i][4] = obj.getString("Type2");
        }
    }


    public String[][] getInfosPokemon() {
        return infosPokemon;
    }

    public String[][] getEvolution() {
        return evolution;
    }

    public String[][] getMegaEvolution() {
        return megaEvolution;
    }

    public String[][] getPokedexEntries() {
        return pokedexEntries;
    }
}
