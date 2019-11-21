package com.example.lab2;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaieskaActivity extends Activity {
    private static final String TAG = "PavadinimasActivity";
    ArrayList<HashMap<String, String>> UzrasaiDataList;


    String pav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        pav = getIntent().getStringExtra("Pavadinimas");
        gautiUzrasus();

    }

    private void gautiUzrasus() {

        new pagalPavadinima().execute(Tools.PaieskaURL, pav.toString(), null);

    }

    private void rodytiUzrasus(List<Uzrasas> uzrasai) {

        UzrasaiDataList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < uzrasai.size(); i++) {
            Uzrasas u = uzrasai.get(i);
            HashMap<String, String> UzrasasDataMap = new HashMap<>();
            UzrasasDataMap.put("id", String.valueOf(u.ID));
            UzrasasDataMap.put("pavadinimas", u.Pavadinimas);
            UzrasasDataMap.put("kategorija", u.Kategorija);

            UzrasaiDataList.add(UzrasasDataMap);
        }
        if(UzrasaiDataList.isEmpty() == false) {

            setContentView(R.layout.uzrasai_list_row2);

            ListView mlv = (ListView) findViewById(R.id.uzrasaiListView);
            SimpleAdapter SimpleMiestaiAdapter = new SimpleAdapter(this, UzrasaiDataList, R.layout.uzrasai_list_row2,
                    new String[]{"pavadinimas", "kategorija"/*, "tekstas", "data", "perziureta"*/},
                    new int[]{R.id.pavadinimasTextView, R.id.kategorijaTextView/*, R.id.tekstasTextView, R.id.dataTextView,R.id.perziureta*/});

            mlv.setAdapter(SimpleMiestaiAdapter);
        }else
            Toast.makeText(getApplicationContext(),"Tokio pavadinimo nėra/šis pavadinimas nepriklausio jokiai kategorijai",Toast.LENGTH_LONG).show();
    }



    public class pagalPavadinima extends AsyncTask<String, String, List<Uzrasas>>{
        ProgressDialog actionProgressDialog = new ProgressDialog(PaieskaActivity.this);

        @Override
        protected void onPreExecute() {
            actionProgressDialog.setMessage("Gaunami užrašai");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(false);
            super.onPreExecute();
        }

        protected List<Uzrasas> doInBackground(String... str_param) {
            String RestURL = str_param[0];
            String data = str_param[1];
            List<Uzrasas> uzrasai = new ArrayList<>();
            try{
                uzrasai = DataAPI.gautiPasirinktus(RestURL, data);
            }catch (Exception ex){
                Log.e(TAG, ex.toString());
            }
            System.out.println(uzrasai.toString());
            return uzrasai;
        }

        protected void onProgressUpdate(Void... progress){

        }

        protected void onPostExecute(List<Uzrasas> result){
            actionProgressDialog.cancel();
            rodytiUzrasus(result);
        }

    }

}
