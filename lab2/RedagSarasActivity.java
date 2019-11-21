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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RedagSarasActivity extends Activity implements AdapterView.OnItemClickListener  {
    private static final String TAG = "RedagSarasActivity";
    ArrayList<HashMap<String, String>> RedagavimoDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.redagavimas_list);
        onActivityResult();
    }

    //----------------------------------------------------------------------------------------------


    protected void onActivityResult( ){

        new RedagSarasActivity.gautiUzrasusTask().execute(Tools.RestURL, null, null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {


       // gautiUzrasus();
        String UzrasoID = RedagavimoDataList.get(pos).get("id");
        String Pavadinimas = RedagavimoDataList.get(pos).get("pavadinimas");
        String Kategorija = RedagavimoDataList.get(pos).get("kategorija");
        String Tekstas = RedagavimoDataList.get(pos).get("tekstas");
        String Data = RedagavimoDataList.get(pos).get("data");

        Intent myIntent = new Intent(this, RedaguotiActivity.class);
        myIntent.putExtra("id", UzrasoID);
        myIntent.putExtra("pavadinimas", Pavadinimas);
        myIntent.putExtra("kategorija", Kategorija);
        myIntent.putExtra("tekstas", Tekstas);
        myIntent.putExtra("data", Data);
        startActivity(myIntent);

    }

    public class gautiUzrasusTask extends AsyncTask<String, String, List<Uzrasas>> {
        ProgressDialog actionProgressDialog = new ProgressDialog(RedagSarasActivity.this);

        @Override
        protected void onPreExecute() {

            actionProgressDialog.setMessage("Gaunamos kategorijos");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(false);
            super.onPreExecute();

        }

        protected List<Uzrasas> doInBackground(String... str_param) {


            String restURL = str_param[0];

            List<Uzrasas> uzrasai = new ArrayList<>();
            try{
                uzrasai = DataAPI.gautiUzrasus(restURL);
            }catch (Exception ex){
                Log.e(TAG, ex.toString());
            }
            return uzrasai;

        }

        protected void onProgressUpdate(Void... progress){

        }

        protected void onPostExecute(List<Uzrasas> result){
            Toast.makeText(RedagSarasActivity.this, "OnPostEx  " , Toast.LENGTH_LONG).show();
            actionProgressDialog.cancel();
            if(result.size() != 0) {
                Toast.makeText(RedagSarasActivity.this, "Rastų kategorijų skaičius: " + result.size(), Toast.LENGTH_SHORT).show();
                rodytiUzrasus(result);
            }else{

                Toast.makeText(RedagSarasActivity.this, "Buvo nerasta jokių kategorijų", Toast.LENGTH_SHORT).show();
            }
        }

    }
    //----------------------------------------------------------------------------------------------







    private void rodytiUzrasus(List<Uzrasas> uzrasai) {



        RedagavimoDataList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < uzrasai.size(); i++) {
            Uzrasas u = uzrasai.get(i);
            HashMap<String, String> UzrasasDataMap = new HashMap<>();
            UzrasasDataMap.put("id", String.valueOf(u.ID));
            UzrasasDataMap.put("pavadinimas", u.Pavadinimas);
            UzrasasDataMap.put("kategorija", u.Kategorija);
            UzrasasDataMap.put("tekstas", u.Tekstas);//mano
            UzrasasDataMap.put("data", String.valueOf(u.DataIrLaikas));//mano
            if(u.Kategorija == "Svarbiausia" )
            RedagavimoDataList.add(UzrasasDataMap);
        }



        ListView mlv = (ListView) findViewById(R.id.uzrasaiListView);

        SimpleAdapter SimpleMiestaiAdapter = new SimpleAdapter(this, RedagavimoDataList, R.layout.redagavimas_list,
                new String[]{"pavadinimas", "kategorija"},
                new int[]{R.id.pavadinimasTextView, R.id.kategorijaTextView});

        mlv.setAdapter(SimpleMiestaiAdapter);
        mlv.setOnItemClickListener(this);
    }
}
