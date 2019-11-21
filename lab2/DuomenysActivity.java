package com.example.lab2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DuomenysActivity extends Activity implements AdapterView.OnItemClickListener {
    private static final String TAG = "DuomenysActivity";
    ArrayList<HashMap<String, String>> UzrasaiDataList;
    String parasytaData;
    String dataNuo = "";
    Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pradinis);
        parasytaData = getIntent().getStringExtra("PARASYTA_DATA");
        setContentView(R.layout.uzrasai_list_row);
        if(parasytaData.length()== 0 ){
           // dataNuo = "";

            gautiUzrasus();
        }
        else {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = fmt.parse(parasytaData);
                onActivityResult(parasytaData);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    //----------------------------------------------------------------------------------------------


    protected void onActivityResult( String DatosEilute){

        new pagalData().execute(Tools.NextURL, DatosEilute, null);

    }

    public class pagalData extends AsyncTask<String, String, List<Uzrasas>>{
        ProgressDialog actionProgressDialog = new ProgressDialog(DuomenysActivity.this);

        @Override
        protected void onPreExecute() {

            actionProgressDialog.setMessage("Gaunami užrašai");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(false);
            super.onPreExecute();
            //Veikia
        }

        protected List<Uzrasas> doInBackground(String... str_param) {

            String nextURL = str_param[0];
            String data = str_param[1];
           List<Uzrasas> uzrasai = new ArrayList<>();
            try{
                uzrasai = DataAPI.gautiPasirinktus(nextURL, data);
            }catch (Exception ex){
                Log.e(TAG, ex.toString());
            }
           return uzrasai;

        }

        protected void onProgressUpdate(Void... progress){

        }

        protected void onPostExecute(List<Uzrasas> result){
            Toast.makeText(DuomenysActivity.this, "OnPostEx  " , Toast.LENGTH_LONG).show();
            actionProgressDialog.cancel();
            if(result.size() != 0) {
                Toast.makeText(DuomenysActivity.this, "Rastų užrašų skaičius: " + result.size(), Toast.LENGTH_SHORT).show();
                rodytiUzrasus(result);
            }else{
                dataNuo = "";
                Toast.makeText(DuomenysActivity.this, "Buvo nerasta jokių užrašų", Toast.LENGTH_SHORT).show();
                }
        }

    }
    //----------------------------------------------------------------------------------------------
    private void gautiUzrasus() {

      new gautiUzrasusTask().execute(Tools.RestURL, null, null);

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {


        gautiUzrasus();
        String UzrasoID = UzrasaiDataList.get(pos).get("id");
        String Pavadinimas = UzrasaiDataList.get(pos).get("pavadinimas");
        String Kategorija = UzrasaiDataList.get(pos).get("kategorija");
        String Tekstas = UzrasaiDataList.get(pos).get("tekstas");
        String Data = UzrasaiDataList.get(pos).get("data");
        String Perziureta = UzrasaiDataList.get(pos).get("perziureta");

        Intent myIntent = new Intent(this, GynimasUzrasasActivity.class);
        myIntent.putExtra("id", UzrasoID);
        myIntent.putExtra("pavadinimas", Pavadinimas);
        myIntent.putExtra("kategorija", Kategorija);
        myIntent.putExtra("tekstas", Tekstas);
        myIntent.putExtra("data", Data);
        myIntent.putExtra("perziureta", Perziureta);
        startActivity(myIntent);

    }



    public class gautiUzrasusTask extends AsyncTask<String, Void, List<Uzrasas>> {
        private static final String TAG = "gautiUzrasusTask";
        ProgressDialog actionProgressDialog = new ProgressDialog(DuomenysActivity.this);


        @Override
        protected void onPreExecute() {

            actionProgressDialog.setMessage("Gaunami užrašai");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(false);
            super.onPreExecute();
        }

        protected List<Uzrasas> doInBackground(String... str_param) {


            String RestURL = str_param[0];
          List<Uzrasas> uzrasai = new ArrayList<>();
            try {
                uzrasai = DataAPI.gautiUzrasus(RestURL);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
           return uzrasai;
        }

        protected void onProgressUpdate(Void... progress) {
        }

        protected void onPostExecute(List<Uzrasas> result) {
            System.out.println("gautiUzrasusTask veikia");

            actionProgressDialog.cancel();
            rodytiUzrasus(result);
        }

    }

    private void rodytiUzrasus(List<Uzrasas> uzrasai) {



        UzrasaiDataList = new ArrayList<HashMap<String, String>>();

//        for (int i = 0; i < uzrasai.size(); i++) {
//            Uzrasas u = uzrasai.get(i);
//            HashMap<String, String> UzrasasDataMap = new HashMap<>();
//            UzrasasDataMap.put("id", String.valueOf(u.ID));
//            UzrasasDataMap.put("pavadinimas", u.Pavadinimas);
//            UzrasasDataMap.put("kategorija", u.Kategorija);
//            UzrasasDataMap.put("tekstas", u.Tekstas);
//            UzrasasDataMap.put("data", String.valueOf(u.DataIrLaikas));
//            UzrasasDataMap.put("perziureta", String.valueOf(u.Perziureta));
//            UzrasaiDataList.add(UzrasasDataMap);
//        }



        for (int i = 0; i < uzrasai.size(); i++) {
            Uzrasas u = uzrasai.get(i);
            if(u.Kategorija.equals("Svarbiausia")) {
                HashMap<String, String> UzrasasDataMap = new HashMap<>();
                UzrasasDataMap.put("id", String.valueOf(u.ID));
                UzrasasDataMap.put("pavadinimas", u.Pavadinimas);
                UzrasasDataMap.put("kategorija", u.Kategorija);
                UzrasasDataMap.put("tekstas", u.Tekstas);//mano
                UzrasasDataMap.put("data", String.valueOf(u.DataIrLaikas));//mano
                UzrasaiDataList.add(UzrasasDataMap);
            }
        }

        for(int i = 0; i < uzrasai.size(); i++)
        {
            Uzrasas u = uzrasai.get(i);
            if(!u.Kategorija.equals("Svarbiausia")) {
                HashMap<String, String> UzrasasDataMap = new HashMap<>();
                UzrasasDataMap.put("id", String.valueOf(u.ID));
                UzrasasDataMap.put("pavadinimas", u.Pavadinimas);
                UzrasasDataMap.put("kategorija", u.Kategorija);
                UzrasasDataMap.put("tekstas", u.Tekstas);//mano
                UzrasasDataMap.put("data", String.valueOf(u.DataIrLaikas));//mano
                UzrasaiDataList.add(UzrasasDataMap);
            }
        }

        ListView mlv = (ListView) findViewById(R.id.uzrasaiListView);

        SimpleAdapter SimpleMiestaiAdapter = new SimpleAdapter(this, UzrasaiDataList, R.layout.uzrasai_list_row,
                new String[]{"pavadinimas", "kategorija","perziureta"},
                new int[]{R.id.pavadinimasTextView, R.id.kategorijaTextView,R.id.perziureta});

        mlv.setAdapter(SimpleMiestaiAdapter);
        mlv.setOnItemClickListener(this);
    }
}
