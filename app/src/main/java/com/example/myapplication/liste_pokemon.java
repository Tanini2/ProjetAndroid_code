package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class liste_pokemon extends AppCompatActivity {

    private String json1;

    private Pokemon pokemon;

    private LinearLayout mLinearListe;

    private RadioGroup mRadioGroup;

    private RadioButton mGen1;
    private RadioButton mGen2;
    private RadioButton mGen3;
    private RadioButton mGen4;
    private RadioButton mGen5;
    private RadioButton mGen6;
    private RadioButton mGen7;
    private RadioButton mGen8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_pokemon);

        mLinearListe = (LinearLayout) findViewById(R.id.Linear_liste);

        mRadioGroup = (RadioGroup) findViewById(R.id.RB_filtres);

        mGen1 = (RadioButton) findViewById(R.id.RB_gen1);
        mGen2 = (RadioButton) findViewById(R.id.RB_gen2);
        mGen3 = (RadioButton) findViewById(R.id.RB_gen3);
        mGen4 = (RadioButton) findViewById(R.id.RB_gen4);
        mGen5 = (RadioButton) findViewById(R.id.RB_gen5);
        mGen6 = (RadioButton) findViewById(R.id.RB_gen6);
        mGen7 = (RadioButton) findViewById(R.id.RB_gen7);
        mGen8 = (RadioButton) findViewById(R.id.RB_gen8);

        //Check le bouton de la génération 1 à l'ouverture de l'activité
        CheckRadioButton(mGen1.getId());

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                CheckRadioButton(checkedId);
            }
        });
    }

    //Va chercher les informations des pokémons dans la base de données selon le # Génération choisi
    private void GetJsonInfosPokemon(String noGeneration){
        //creating asynctask object and executing it
        try {
            json1 = new JSON().execute("http://www.dicj.info/etu/vanta1337589/Android/FiltreGenerations.php?noGeneration=" + noGeneration).get();
            pokemon = new Pokemon(json1);
            LoadList();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    //Change le nom des pokémon n'ayant pas le même nom que leur image
    private String ReturnNameForPicture(String name){
        String nomCorrige;
        switch(name)
        {
            case "Nidoran (F)":
                nomCorrige = "nidoranf";
                break;
            case "Nidoran (M)":
                nomCorrige = "nidoranm";
                break;
            case "Farfetch'd":
                nomCorrige = "farfetchd";
                break;
            case "Mr.Mime":
                nomCorrige = "mrmime";
                break;
            case "Ho-Oh":
                nomCorrige = "hooh";
                break;
            case "Mime Jr.":
                nomCorrige = "mimejr";
                break;
            case "Porygon-Z":
                nomCorrige = "porygonz";
                break;
            case "Flabébé":
                nomCorrige = "flabebe";
                break;
            case "Type: Null":
                nomCorrige = "typenull";
                break;
            case "Jangmo-o":
                nomCorrige = "jangmoo";
                break;
            case "Hakamo-o":
                nomCorrige = "hakamoo";
                break;
            case "Kommo-o":
                nomCorrige = "kommoo";
                break;
            case "Tapu Koko":
                nomCorrige = "tapukoko";
                break;
            case "Tapu Lele":
                nomCorrige = "tapulele";
                break;
            case "Tapu Bulu":
                nomCorrige = "tapubulu";
                break;
            case "Tapu Fini":
                nomCorrige = "tapufini";
                break;
            case "Sirfetch'd":
                nomCorrige = "sirfetchd";
                break;
            case "Mr. Rime":
                nomCorrige = "mrrime";
                break;
            default:
                nomCorrige = name.toLowerCase();
                break;
        }

        return nomCorrige;
    }

    //Vérifie quel RadioButton a été coché
    private void CheckRadioButton(int checkedRadioID){
        if(checkedRadioID == mGen1.getId()){
            GetJsonInfosPokemon(mGen1.getTag().toString());
        }
        else if(checkedRadioID == mGen2.getId()){
            GetJsonInfosPokemon(mGen2.getTag().toString());
        }
        else if(checkedRadioID == mGen3.getId()){
            GetJsonInfosPokemon(mGen3.getTag().toString());
        }
        else if(checkedRadioID == mGen4.getId()){
            GetJsonInfosPokemon(mGen4.getTag().toString());
        }
        else if(checkedRadioID == mGen5.getId()){
            GetJsonInfosPokemon(mGen5.getTag().toString());
        }
        else if(checkedRadioID == mGen6.getId()){
            GetJsonInfosPokemon(mGen6.getTag().toString());
        }
        else if(checkedRadioID == mGen7.getId()){
            GetJsonInfosPokemon(mGen7.getTag().toString());
        }
        else if(checkedRadioID == mGen8.getId()){
            GetJsonInfosPokemon(mGen8.getTag().toString());
        }
    }

    //Crée une liste de Pokémon à partir de la liste reçue en JSON
    private void LoadList(){
        String[][] InfosPok;

        int noPokemon = 0;

        View vPrecedent = null;

        InfosPok = pokemon.getInfosPokemon();

        //S'il y a quelque chose dans la liste, la vide avant de mettre les nouvelles données dedans
        if(mLinearListe.getChildCount() > 0){
            mLinearListe.removeAllViews();
        }

        LayoutInflater layoutInflater = LayoutInflater.from(liste_pokemon.this);
        //Si InfosPok n'est pas vide, rempli autant de layout qu'il y a d'entrées dans InfosPok
        if(InfosPok.length != 0){
            for (int i = 0; i < InfosPok.length; i++) {
                //Si le noPokémon de InfosPok est égal à la variable noPokemon, on inflate seulement la partie type et on l'ajoute à la vue précédente
                if (Integer.parseInt(InfosPok[i][1]) == noPokemon) {
                    View view2 = layoutInflater.inflate(R.layout.textview_types, null);
                    if (InfosPok[i][4] != "null") {
                        ((TextView) view2.findViewById(R.id.types)).setText(InfosPok[i][3] + " / " + InfosPok[i][4]);
                    } else {
                        ((TextView) view2.findViewById(R.id.types)).setText(InfosPok[i][3]);
                    }
                    ((LinearLayout) vPrecedent.findViewById(R.id.partie_types)).addView(view2);
                //Sinon on inflate un layout complet
                } else {
                    View view = layoutInflater.inflate(R.layout.bloc_liste_pokemon, null);
                    vPrecedent = view;
                    ((TextView) view.findViewById(R.id.no_nom_pokemon)).setText("#" + InfosPok[i][1] + " " + InfosPok[i][2]);
                    ((TextView) view.findViewById(R.id.no_nom_pokemon)).setTag(Integer.parseInt(InfosPok[i][1]));
                    ((TextView) view.findViewById(R.id.no_nom_pokemon)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Ouvre l'activité fiche_pokemon et lui passe en paramètre le tag de la vue, soit le # du Pokémon
                            Intent intent = fiche_pokemon.newIntent(liste_pokemon.this, (int) v.getTag());
                            startActivity(intent);
                        }
                    });
                    View view2 = layoutInflater.inflate(R.layout.textview_types, null);
                    ImageView view3 = ((ImageView) view.findViewById(R.id.img_pokemonListe));
                    String nameE = InfosPok[i][2];
                    view3.setId(Integer.parseInt(InfosPok[i][1]));
                    Picasso.get().load("http://www.dicj.info/etu/vanta1337589/Pokedex/images/Pokemon/" + ReturnNameForPicture(nameE) + ".png").resize(100, 100).into(view3);
                    view3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Ouvre l'activité fiche_pokemon et lui passe en paramètre le tag de la vue, soit le # du Pokémon
                            Intent intent = fiche_pokemon.newIntent(liste_pokemon.this, v.getId());
                            startActivity(intent);
                        }
                    });
                    ((TextView) view2.findViewById(R.id.types)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                    //Si le Pokémon a un deuxième type, on affiche les deux
                    if (InfosPok[i][4] != "null") {
                        ((TextView) view2.findViewById(R.id.types)).setText(InfosPok[i][3] + " / " + InfosPok[i][4]);
                    //Sinon on affiche seulement le premier type
                    } else {
                        ((TextView) view2.findViewById(R.id.types)).setText(InfosPok[i][3]);
                    }
                    ((LinearLayout) view.findViewById(R.id.partie_types)).addView(view2);
                    mLinearListe.addView(view);

                }
                //On garde le numéro de Pokémon pour ceux ayant plusieurs 2e types
                noPokemon = Integer.parseInt(InfosPok[i][1]);
            }
        }
        //Si InfosPok est vide, affiche l'image à venir
        else{
            View view = layoutInflater.inflate(R.layout.bloc_a_venir, null);
            ImageView view3 = ((ImageView) view.findViewById(R.id.image_a_venir));
            Picasso.get().load("http://www.dicj.info/etu/vanta1337589/Pokedex/images/coming_soon_liste.png").into(view3);
            mLinearListe.addView(view);
        }
    }
}
