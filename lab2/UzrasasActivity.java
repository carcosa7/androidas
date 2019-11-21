package com.example.lab2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class UzrasasActivity extends Activity {
    private static final String TAG = "UzrasasActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uzrasai_list_row2);

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

//SITAS JEI TIESIOG NORI ATSPAUSDINT UZRASUS - nereikes is laukeliu imt duomenu---------------------
        ListView mlv = (ListView) findViewById(R.id.uzrasaiListView);
        SimpleAdapter SimpleMiestaiAdapter = new SimpleAdapter(this, UzrasaiDataList, R.layout.uzrasai_list_row2,
                new String[]{"pavadinimas", "kategorija", "tekstas","data", "perziureta"},
                new int[]{R.id.pavadinimasTextView, R.id.kategorijaTextView, R.id.tekstasTextView, R.id.dataTextView,R.id.perziureta});

        mlv.setAdapter(SimpleMiestaiAdapter);

        //------------------------------------------------------------------------------------------------------------
        System.out.println("Response Code **********************************1 ");

    }



    private void Atnaujinimas() {

        new Update().execute(Tools.SkaitytaURL, getIntent().getStringExtra("id"), "Skaityta");

    }

    public class Update extends AsyncTask<String, String, String> {
        ProgressDialog actionProgressDialog = new ProgressDialog(UzrasasActivity.this);

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
