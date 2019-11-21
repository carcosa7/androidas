package com.example.lab2;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrynimasActivity extends Activity implements AdapterView.OnItemClickListener {
    private static final String TAG = "TrynimasActivity";
    ArrayList<HashMap<String, String>> TrynimasDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.uzrasai_list_row2);
        onActivityResult();

    }

    //----------------------------------------------------------------------------------------------


    protected void onActivityResult( ){
        //dataNuo = DatosEilute ;
        new spausdintiVisus().execute(Tools.RestURL, null, "Spausdinti");
    }

    protected void onTrynimas( String UzrasoID ){
        //dataNuo = DatosEilute ;
        new TrintiUzrasus().execute(Tools.TrintiURL, UzrasoID, "Trinti");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        // onActivityResult();
        //int UzrasoID = Integer.parseInt(TrynimasDataList.get(pos).get("id"));
        String UzrasoID = TrynimasDataList.get(pos).get("id");
        onTrynimas(UzrasoID);
//        Intent intent = new Intent(TrynimasActivity.this, SpausdintiIstrintusActivity.class);
//        startActivity(intent);
        onActivityResult();
    }



    public class spausdintiVisus extends AsyncTask<String, String, List<Uzrasas>>{

        ProgressDialog actionProgressDialog = new ProgressDialog(TrynimasActivity.this);

        @Override
        protected void onPreExecute() {

            actionProgressDialog.setMessage("Gaunami užrašai");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(false);
            super.onPreExecute();
        }

        protected List<Uzrasas> doInBackground(String... str_param) {

            String UzrasoID = null;
            String veiksmas = str_param[2];
            String getURL = str_param[0];



            List<Uzrasas> uzrasai = new ArrayList<>();
            try{
                 uzrasai = DataAPI.gautiUzrasus(getURL);
            }catch (Exception ex){
                Log.e(TAG, ex.toString());
            }
            return uzrasai;

        }


        protected void onProgressUpdate(Void... progress){

        }

        protected void onPostExecute(List<Uzrasas> result){
            Toast.makeText(TrynimasActivity.this, "OnPostEx  " , Toast.LENGTH_LONG).show();
            actionProgressDialog.cancel();
            if(result.size() != 0) {
                Toast.makeText(TrynimasActivity.this, "Rastų užrašų skaičius: " + result.size(), Toast.LENGTH_SHORT).show();

                rodytiUzrasus(result);
            }else{

                Toast.makeText(TrynimasActivity.this, "Buvo nerasta jokių užrašų", Toast.LENGTH_SHORT).show();
            }
        }

    }
    //----------------------------------------------------------------------------------------------


    public class TrintiUzrasus extends AsyncTask<String, String, List<Uzrasas>>{
        ProgressDialog actionProgressDialog2 = new ProgressDialog(TrynimasActivity.this);

        @Override
        protected void onPreExecute() {

            actionProgressDialog2.setMessage("Trinami užrašai");
            actionProgressDialog2.show();
            actionProgressDialog2.setCancelable(false);
            super.onPreExecute();

        }

        protected List<Uzrasas> doInBackground(String... str_param) {

            String UzrasoID = null;
            String veiksmas = str_param[2];
            String getURL = str_param[0];
            UzrasoID =str_param[1];




            List<Uzrasas> uzrasai = new ArrayList<>();
            try{
                    DataAPI.trintiUzrasus(getURL,UzrasoID);

            }catch (Exception ex){
                Log.e(TAG, ex.toString());
            }
            return null;

        }


        protected void onPostExecute(List<Uzrasas> result){
            Toast.makeText(TrynimasActivity.this, "OnPostEx  " , Toast.LENGTH_LONG).show();
           actionProgressDialog2.cancel();
                Toast.makeText(TrynimasActivity.this, "Uzrasas istrintas" , Toast.LENGTH_SHORT).show();
        }
    }


    private void rodytiUzrasus(List<Uzrasas> uzrasai) {

        TrynimasDataList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < uzrasai.size(); i++) {
            Uzrasas u = uzrasai.get(i);
            HashMap<String, String> UzrasasDataMap = new HashMap<>();
            UzrasasDataMap.put("id", String.valueOf(u.ID));
            UzrasasDataMap.put("pavadinimas", u.Pavadinimas);
            UzrasasDataMap.put("kategorija", u.Kategorija);
            UzrasasDataMap.put("tekstas", u.Tekstas);//mano
            UzrasasDataMap.put("data", String.valueOf(u.DataIrLaikas));//mano
            TrynimasDataList.add(UzrasasDataMap);
        }

        ListView mlv = (ListView) findViewById(R.id.uzrasaiListView);

        SimpleAdapter SimpleMiestaiAdapter = new SimpleAdapter(this, TrynimasDataList, R.layout.uzrasai_list_row2,
                new String[]{"pavadinimas", "kategorija"},
                new int[]{R.id.pavadinimasTextView, R.id.kategorijaTextView});

        mlv.setAdapter(SimpleMiestaiAdapter);
        mlv.setOnItemClickListener(this);
    }
}
