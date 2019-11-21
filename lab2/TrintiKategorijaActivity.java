package com.example.lab2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrintiKategorijaActivity extends Activity  {

    private static final String TAG = "TrintiKategActivity";
    ArrayList<HashMap<String, String>> KategorijosDataList;
String kategorija = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.kategorijos);
        kategorija = getIntent().getStringExtra("Kategorija");
        onActivityResult();
    }

    protected void onActivityResult( ){
        new TrintiKategorijaActivity.trintiUzrasusTask().execute(Tools.TrintKategURL, kategorija, null);
    }


    private class trintiUzrasusTask extends AsyncTask<String, Void, List<Uzrasas>> {
        ProgressDialog actionProgressDialog = new ProgressDialog(TrintiKategorijaActivity.this);

        @Override
        protected void onPreExecute() {
            actionProgressDialog.setMessage("Trinami užrašai...");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(true);
            super.onPreExecute();
        }

        protected List<Uzrasas> doInBackground(String... str_param) {
            String RestURL = str_param[0];
            String kateg = str_param[1];
            List<Uzrasas> uzrasai = new ArrayList<Uzrasas>();
            try {

                WebAPI.trintiKateg(RestURL,kateg);

            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
            return null;
        }

        protected void onProgressUpdate(Void... progress) {
        }

        protected void onPostExecute(List<Uzrasas> result) {
            actionProgressDialog.cancel();
            Intent intent = new Intent(TrintiKategorijaActivity.this, DuomenysActivity.class);

            intent.putExtra("PARASYTA_DATA", "");
            startActivity(intent);

        }

    }
}
