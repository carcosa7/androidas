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

public class RusiavimasActivity extends Activity implements AdapterView.OnItemClickListener  {
    private static final String TAG = "RusiavimasActivity";
    ArrayList<HashMap<String, String>> RusiavimasDataList;
    String rusTipas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rusTipas = getIntent().getStringExtra("Rusiavimo_Tipas");
        setContentView(R.layout.uzrasai_list_row);

         if(rusTipas.compareTo("Didejanciai") == 0 )
             RusiuotDidejanciai();
        if(rusTipas.compareTo("Mazejanciai") == 0 )
            RusiuotMazejanciai();
    }

    //----------------------------------------------------------------------------------------------


    protected void RusiuotDidejanciai( ){

        new RusiavimasActivity.gautiUzrasusTask().execute(Tools.RusDidejanciaiURL, null, null);

    }
    protected void RusiuotMazejanciai( ){

        new RusiavimasActivity.gautiUzrasusTask().execute(Tools.RusMazejanciaiURL, null, null);

    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public class gautiUzrasusTask extends AsyncTask<String, String, List<Uzrasas>> {
        ProgressDialog actionProgressDialog = new ProgressDialog(RusiavimasActivity.this);

        @Override
        protected void onPreExecute() {

            actionProgressDialog.setMessage("Gaunami uzrasai");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(false);
            super.onPreExecute();

        }

        protected List<Uzrasas> doInBackground(String... str_param) {

            String katURL = str_param[0];

            List<Uzrasas> uzrasai = new ArrayList<>();
            try{
                uzrasai = DataAPI.gautiUzrasus(katURL);
            }catch (Exception ex){
                Log.e(TAG, ex.toString());
            }
            return uzrasai;

        }

        protected void onProgressUpdate(Void... progress){

        }

        protected void onPostExecute(List<Uzrasas> result){
            Toast.makeText(RusiavimasActivity.this, "OnPostEx  " , Toast.LENGTH_LONG).show();
            actionProgressDialog.cancel();
            if(result.size() != 0) {
                Toast.makeText(RusiavimasActivity.this, "Rastų uzrasu skaičius: " + result.size(), Toast.LENGTH_SHORT).show();
                rodytiUzrasus(result);
            }else{

                Toast.makeText(RusiavimasActivity.this, "Buvo nerasta jokių uzrasu", Toast.LENGTH_SHORT).show();
                }
        }

    }
    //----------------------------------------------------------------------------------------------







    private void rodytiUzrasus(List<Uzrasas> uzrasai) {



        RusiavimasDataList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < uzrasai.size(); i++) {
            Uzrasas u = uzrasai.get(i);
            HashMap<String, String> UzrasasDataMap = new HashMap<>();
            UzrasasDataMap.put("kategorija", String.valueOf(u.Kategorija));
            UzrasasDataMap.put("pavadinimas", u.Pavadinimas);
            RusiavimasDataList.add(UzrasasDataMap);
        }

        ListView mlv = (ListView) findViewById(R.id.uzrasaiListView);

        SimpleAdapter SimpleMiestaiAdapter = new SimpleAdapter(this, RusiavimasDataList, R.layout.uzrasai_list_row,
                new String[]{"pavadinimas","kategorija"},
                new int[]{R.id.pavadinimasTextView,R.id.kategorijaTextView});

        mlv.setAdapter(SimpleMiestaiAdapter);
        mlv.setOnItemClickListener(this);
    }
}
