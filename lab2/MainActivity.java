package com.example.lab2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity /*implements AdapterView.OnItemClickListener */{
    private static final String TAG = "MainActivity";

    Button rodytiDuomenis, kateg, iterptNauja, rusDidejanciai, rusMazejanciai,trinamiUzras,redaguot,ieskot,trintiKat;
    EditText parasytaData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.pradinis);
        rodytiDuomenis = (Button) findViewById(R.id.Rodyti);//Mano mygtukas
        kateg = (Button) findViewById(R.id.Kategorijos);
        iterptNauja = (Button) findViewById(R.id.IterptiNauja);
        parasytaData = (EditText) findViewById(R.id.Data);
        rusDidejanciai = (Button) findViewById(R.id.rusiuotDid);
        rusMazejanciai = (Button) findViewById(R.id.rusiuotMaz);
        trinamiUzras = (Button) findViewById(R.id.trinti);
        redaguot = (Button) findViewById(R.id.Redaguot);
        ieskot = (Button) findViewById(R.id.Paieska);
        trintiKat = (Button) findViewById(R.id.trintKateg);


        if( Tools.IsOnline( getApplicationContext() ) ) {
            System.out.println("prisijungta");
        }



        rodytiDuomenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DuomenysActivity.class);

                intent.putExtra("PARASYTA_DATA", (Serializable) parasytaData.getText().toString());
                startActivity(intent);
            }
        });

        kateg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KategorijosActivity.class);

                startActivity(intent);
            }
        });

        iterptNauja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Iterpti_IrasaActivity.class);

                startActivity(intent);
            }
        });

        rusDidejanciai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RusiavimasActivity.class);

                intent.putExtra("Rusiavimo_Tipas",  "Didejanciai");
                startActivity(intent);
            }
        });

        rusMazejanciai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RusiavimasActivity.class);

                intent.putExtra("Rusiavimo_Tipas",  "Mazejanciai");
                startActivity(intent);
            }
        });

        trinamiUzras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrynimasActivity.class);

                startActivity(intent);
            }
        });
        redaguot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RedagSarasActivity.class);

                startActivity(intent);
            }
        });
        ieskot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PaieskaActivity.class);
                String pav = parasytaData.getText().toString();
                intent.putExtra("Pavadinimas",  pav);
                startActivity(intent);
            }
        });


        trintiKat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kateg = parasytaData.getText().toString();
              if(kateg.equals("Kita") ||kateg.equals("Svarbu")|| kateg.equals("Darbas") ) {
                  Intent intent = new Intent(MainActivity.this, TrintiKategorijaActivity.class);
                  intent.putExtra("Kategorija", kateg);
                  startActivity(intent);
              } else
              {
                  Toast.makeText(MainActivity.this, "Kategorijos turi but: Svarbu/Kita/Darbas", Toast.LENGTH_LONG).show();
              }
            }
        });
    }

}







