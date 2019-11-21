package com.example.lab2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class GynimasUzrasasActivity extends Activity   {
    private static final String TAG = "GynimasUzrasasActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uzrasai_list_row2_keiciamas);

        Button issaugoti = findViewById(R.id.Issaugoti2);
        final EditText aprasas = findViewById(R.id.tekstasTextView2);
        final TextView data = findViewById(R.id.dataTextView2);
        final TextView kategorijaText = findViewById(R.id.kategorijaTextView2);
        final TextView pavadinimas = findViewById(R.id.pavadinimasTextView2);

        String tekstas = getIntent().getStringExtra("tekstas");
        aprasas.setText(getIntent().getStringExtra("tekstas"));
        kategorijaText.setText(getIntent().getStringExtra("kategorija"));
        pavadinimas.setText(getIntent().getStringExtra("pavadinimas"));
        data.setText(getIntent().getStringExtra("data"));



        HashMap<String, String> UzrasasDataMap = new HashMap<>();
        ArrayList<HashMap<String, String>> UzrasaiDataList= new ArrayList<> ();
        UzrasasDataMap.put("id", getIntent().getStringExtra("id"));
        UzrasasDataMap.put("pavadinimas", getIntent().getStringExtra("pavadinimas"));
        UzrasasDataMap.put("kategorija", getIntent().getStringExtra("kategorija"));
        UzrasasDataMap.put("tekstas", getIntent().getStringExtra("tekstas"));
        UzrasasDataMap.put("data", getIntent().getStringExtra("data"));
        UzrasasDataMap.put("perziureta", "Taip");
        UzrasaiDataList.add(UzrasasDataMap);

        Atnaujinimas();
        //------------------------------------------------------------------------------------------------------------
        System.out.println("Response Code **********************************1 ");

        issaugoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kategorija ="";
                if(getIntent().getStringExtra("kategorija").equals("Darbas"))
                    kategorija = "2";
                else if(getIntent().getStringExtra("kategorija").equals("Svarbu"))
                    kategorija = "1";
                else
                    kategorija = "3";
                String tekstas = aprasas.getText().toString();
                String reiksmes = getIntent().getStringExtra("id") + ";" + getIntent().getStringExtra("pavadinimas") + ";" +   tekstas+ ";"  + kategorija;
                Update(reiksmes);
            }
        });

    }

    private void Atnaujinimas() {

        new Update().execute(Tools.SkaitytaURL, getIntent().getStringExtra("id"), "Skaityta");

    }
    private void Update(String reiksmes) {


        new GynimasUzrasasActivity.updateTask().execute(Tools.RedaguotiURL, reiksmes);
    }

    public class updateTask extends AsyncTask<String, String, String> {
        ProgressDialog actionProgressDialog = new ProgressDialog(GynimasUzrasasActivity.this);

        @Override
        protected void onPreExecute() {
            actionProgressDialog.setMessage("Atnaujinimas užrašas");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(false);
            super.onPreExecute();
        }

        protected String doInBackground(String... str_param) {
            String RestURL = str_param[0];
            String id = str_param[1];
            String rez = "";
            //List<Uzrasas> uzrasai = new ArrayList<>();
            try {
                rez = DataAPI.Redaguoti(RestURL, id);
                //uzrasai = DataAPI.gautiUzrasus(RestURL);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
            return rez;
        }

        protected void onProgressUpdate(Void... progress) {

        }

        protected void onPostExecute(String rez) {
            actionProgressDialog.cancel();
            System.out.println(rez);
            if (rez != "\"nera\"" || rez != "\"no\"" || rez != "\"truksta\"") {
                Toast.makeText(GynimasUzrasasActivity.this, "Užrašas sėkmingai atnaujintas", Toast.LENGTH_SHORT).show();
                //new MainActivity().salinimas();
            }else {
                Toast.makeText(GynimasUzrasasActivity.this, "Užrašas nebuvo atnaujintass", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class Update extends AsyncTask<String, String, String> {
        ProgressDialog actionProgressDialog = new ProgressDialog(GynimasUzrasasActivity.this);

        @Override
       /* protected void onPreExecute() {
            actionProgressDialog.setMessage("Gaunami užrašai");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(false);
            super.onPreExecute();
        }*/

        protected String doInBackground(String... str_param) {
            String RestURL = str_param[0];
            String id = str_param[1];
            String busena = str_param[2];


            try {
                DataAPI.ArSakityta(RestURL, id,busena);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }

            return "s";
        }
    }
}
