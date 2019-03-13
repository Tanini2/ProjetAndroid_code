package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import java.util.concurrent.ExecutionException;

public class fiche_pokemon extends AppCompatActivity {

    private int noPokemon;

    private String json1;
    private String json2;
    private String json3;
    private String json4;

    private Pokemon pokemon;

    private TextView mNoNomPok;

    private ImageView mImgPok;

    private LinearLayout mSectionEvo;
    private LinearLayout mLinearEvo;
    private LinearLayout mLinearMegaEvo;
    private LinearLayout mSectionMegaEvo;
    private LinearLayout mLinearTypes;

    private TableLayout mTablePokEntries;

    private static final String EXTRA_NOPOKEDEX = "com.example.myapplication.noPokedex";

    public static Intent newIntent(Context packageContext, int noPokedex){
        Intent intent = new Intent(packageContext, fiche_pokemon.class);

        intent.putExtra(EXTRA_NOPOKEDEX, noPokedex);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_pokemon);

        String[][] InfosPok;
        final String[][] EvoPok;
        String[][] MegaEvoPok;
        String[][] PokEntries;

        noPokemon = getIntent().getIntExtra(EXTRA_NOPOKEDEX, 0);

        mNoNomPok = (TextView) findViewById(R.id.no_nom_pok);

        mImgPok = (ImageView) findViewById(R.id.img_pokemon);

        mSectionEvo = (LinearLayout) findViewById(R.id.evolution_section);
        mLinearEvo = (LinearLayout) findViewById(R.id.Linear_evo);
        mLinearMegaEvo = (LinearLayout) findViewById(R.id.Linear_megaEvo);
        mSectionMegaEvo = (LinearLayout) findViewById(R.id.mega_evolution_section);
        mLinearTypes = (LinearLayout) findViewById(R.id.Linear_types);

        mTablePokEntries = (TableLayout) findViewById(R.id.Table_pokedex_entries);

        GetJsonInfosPokemon();

        InfosPok = pokemon.getInfosPokemon();
        EvoPok = pokemon.getEvolution();
        MegaEvoPok = pokemon.getMegaEvolution();
        PokEntries = pokemon.getPokedexEntries();

        String name = InfosPok[0][2];

        //Section InfosPokémon
        mNoNomPok.setText("#" + InfosPok[0][1] + " " + InfosPok[0][2]);

        LayoutInflater layoutInflater = LayoutInflater.from(fiche_pokemon.this);

        if(InfosPok.length > 1){
            for (int i = 0; i <InfosPok.length; i++){
                View view = layoutInflater.inflate(R.layout.textview_types, null);
                ((TextView)view.findViewById(R.id.types)).setText(InfosPok[0][3] + " / " + InfosPok[i][4]);
                mLinearTypes.addView(view);
            }
        }
        else{
            View view = layoutInflater.inflate(R.layout.textview_types, null);
            if(InfosPok[0][4] != "null"){
                ((TextView)view.findViewById(R.id.types)).setText(InfosPok[0][3] + " / " + InfosPok[0][4]);
            }
            else{
                ((TextView)view.findViewById(R.id.types)).setText(InfosPok[0][3]);
            }
            mLinearTypes.addView(view);
        }
        Picasso.get().load("http://www.dicj.info/etu/vanta1337589/Pokedex/images/Pokemon/" + ReturnNameForPicture(name) + ".png").into(mImgPok);

        //Section EvolutionPokemon
        if(EvoPok[0][0] == ""){
            SetVisibilityLayout(mSectionEvo, 8);
        }
        else{
            SetVisibilityLayout(mSectionEvo, 0);

            for (int i = 0; i <EvoPok.length; i++){
                View view = layoutInflater.inflate(R.layout.bloc_evolution_pokemon, null);
                ((TextView)view.findViewById(R.id.text_no_nomE)).setText("# " + EvoPok[i][0] + " " + EvoPok[i][1]);
                ((TextView)view.findViewById(R.id.text_type_descriptionE)).setText(EvoPok[i][2] + " : " + EvoPok[i][3]);
                ImageView view2 = ((ImageView)view.findViewById(R.id.img_pokE));
                String nameE = EvoPok[i][1];
                view2.setId(Integer.parseInt(EvoPok[i][0]));
                Picasso.get().load("http://www.dicj.info/etu/vanta1337589/Pokedex/images/Pokemon/" + ReturnNameForPicture(nameE) + ".png").into(view2);
                view2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = fiche_pokemon.newIntent(fiche_pokemon.this, v.getId());
                        startActivity(intent);
                    }
                });
                mLinearEvo.addView(view);
            }

        }

        //Section Mega Evolution Pokemon
        if(MegaEvoPok[0][0] == ""){
            SetVisibilityLayout(mSectionMegaEvo, 8);
        }
        else{
            SetVisibilityLayout(mSectionMegaEvo, 0);

            for (int i = 0; i <MegaEvoPok.length; i++){
                View view = layoutInflater.inflate(R.layout.bloc_evolution_pokemon, null);
                ((TextView)view.findViewById(R.id.text_no_nomE)).setText(MegaEvoPok[i][0] + " / " + MegaEvoPok[i][1]);
                ((TextView)view.findViewById(R.id.text_type_descriptionE)).setText(MegaEvoPok[i][2]);
                ImageView view2 = ((ImageView)view.findViewById(R.id.img_pokE));
                if(MegaEvoPok.length > 1){
                    if(i == 0) {
                        Picasso.get().load("http://www.dicj.info/etu/vanta1337589/Pokedex/images/Pokemon/mega_" + ReturnNameForPicture(name) + "_x.png").into(view2);
                    }
                    else{
                        Picasso.get().load("http://www.dicj.info/etu/vanta1337589/Pokedex/images/Pokemon/mega_" + ReturnNameForPicture(name)  + "_y.png").into(view2);
                    }
                }
                else{
                    Picasso.get().load("http://www.dicj.info/etu/vanta1337589/Pokedex/images/Pokemon/mega_" + ReturnNameForPicture(name)  + ".png").into(view2);
                }
                mLinearMegaEvo.addView(view);
            }
        }

        //Section Pokedex Entries
        int compteurGeneration = 0;

        for(int i = 0; i < PokEntries.length; i ++){
            if(Integer.parseInt(PokEntries[i][2]) != compteurGeneration){
                compteurGeneration = Integer.parseInt(PokEntries[i][2]);
                View view = layoutInflater.inflate(R.layout.table_row_generation, null);
                ((TextView)view.findViewById(R.id.row_generation)).setText("Generation " + PokEntries[i][2] );
                mTablePokEntries.addView(view);
            }
            View view = layoutInflater.inflate(R.layout.table_row_entry, null);
            ((TextView)view.findViewById(R.id.row_jeu)).setText(PokEntries[i][0]);
            ((TextView)view.findViewById(R.id.row_entry)).setText(PokEntries[i][1]);
            mTablePokEntries.addView(view);
        }
    }

    //Crée le pokémon à partir du JSON
    private Pokemon CreatePokemon(String json1, String json2, String json3, String json4)throws JSONException{
        pokemon = new Pokemon(json1, json2, json3, json4);
        return pokemon;
    }
    //Set la visibilité des layouts et de leurs enfants
    private void SetVisibilityLayout(LinearLayout layoutParent, int visibility){
        layoutParent.setVisibility(visibility);
        LinearLayout layout = layoutParent;
        int count = layout.getChildCount();
        View v = null;
        for(int i=0; i<count; i++) {
            v = layout.getChildAt(i);
            v.setVisibility(visibility);
        }
    }
    //Crée les String JSON et les remplit
    private void GetJsonInfosPokemon(){
        //creating asynctask object and executing it
        try {
            json1 = new JSON().execute("http://www.dicj.info/etu/vanta1337589/Android/InfosPokemon.php?noPokemon=" + noPokemon).get();
            json2 = new JSON().execute("http://www.dicj.info/etu/vanta1337589/Android/Evolution.php?noPokemon=" + noPokemon).get();
            json3 = new JSON().execute("http://www.dicj.info/etu/vanta1337589/Android/MegaEvolutions.php?noPokemon=" + noPokemon).get();
            json4 = new JSON().execute("http://www.dicj.info/etu/vanta1337589/Android/PokedexEntries.php?noPokemon=" + noPokemon).get();
            pokemon = CreatePokemon(json1,json2, json3, json4);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

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
            default:
                nomCorrige = name.toLowerCase();
                break;
        }

        return nomCorrige;
    }
}
