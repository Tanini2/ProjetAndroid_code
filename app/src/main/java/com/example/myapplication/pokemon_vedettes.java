package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;


public class pokemon_vedettes extends AppCompatActivity {

    ImageView ImagePok1;
    ImageView ImagePok2;
    ImageView ImagePok3;
    ImageView ImagePok4;
    ImageView ImagePok5;
    ImageView ImagePok6;

    int NoPokedex;

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_vedettes);

        ImagePok1 = (ImageView) findViewById(R.id.ImagePok1);
        ImagePok2 = (ImageView) findViewById(R.id.ImagePok2);
        ImagePok3 = (ImageView) findViewById(R.id.ImagePok3);
        ImagePok4 = (ImageView) findViewById(R.id.ImagePok4);
        ImagePok5 = (ImageView) findViewById(R.id.ImagePok5);
        ImagePok6 = (ImageView) findViewById(R.id.ImagePok6);
        mButton = (Button) findViewById(R.id.button_voirTout);

        Picasso.get().load("http://www.dicj.info/etu/vanta1337589/Pokedex/images/Pokemon/bulbasaur.png").into(ImagePok1);
        Picasso.get().load("http://www.dicj.info/etu/vanta1337589/Pokedex/images/Pokemon/charizard.png").into(ImagePok2);
        Picasso.get().load("http://www.dicj.info/etu/vanta1337589/Pokedex/images/Pokemon/entei.png").into(ImagePok3);
        Picasso.get().load("http://www.dicj.info/etu/vanta1337589/Pokedex/images/Pokemon/greninja.png").into(ImagePok4);
        Picasso.get().load("http://www.dicj.info/etu/vanta1337589/Pokedex/images/Pokemon/eevee.png").into(ImagePok5);
        Picasso.get().load("http://www.dicj.info/etu/vanta1337589/Pokedex/images/Pokemon/wormadam.png").into(ImagePok6);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pokemon_vedettes.this, liste_pokemon.class);
                startActivity(intent);
            }
        });

        ImagePok1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoPokedex = 1;
                Intent intent = fiche_pokemon.newIntent(pokemon_vedettes.this, NoPokedex);
                startActivity(intent);
            }
        });
        ImagePok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoPokedex = 6;
                Intent intent = fiche_pokemon.newIntent(pokemon_vedettes.this, NoPokedex);
                startActivity(intent);
            }
        });
        ImagePok3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoPokedex = 244;
                Intent intent = fiche_pokemon.newIntent(pokemon_vedettes.this, NoPokedex);
                startActivity(intent);
            }
        });
        ImagePok4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoPokedex = 658;
                Intent intent = fiche_pokemon.newIntent(pokemon_vedettes.this, NoPokedex);
                startActivity(intent);
            }
        });
        ImagePok5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoPokedex = 133;
                Intent intent = fiche_pokemon.newIntent(pokemon_vedettes.this, NoPokedex);
                startActivity(intent);
            }
        });
        ImagePok6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoPokedex = 413;
                Intent intent = fiche_pokemon.newIntent(pokemon_vedettes.this, NoPokedex);
                startActivity(intent);
            }
        });

    }
}
