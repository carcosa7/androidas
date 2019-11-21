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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KategorijosActivity extends Activity implements AdapterView.OnItemClickListener  {
    private static final String TAG = "KategorijosActivity";
    ArrayList<HashMap<String, String>> KategorijosDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.kategorijos);
        onActivityResult();

    }

    //----------------------------------------------------------------------------------------------


    protected void onActivityResult( ){
       new KategorijosActivity.gautiUzrasusTask().execute(Tools.KategorijaURL, null, null);
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
        ProgressDialog actionProgressDialog = new ProgressDialog(KategorijosActivity.this);

        @Override
        protected void onPreExecute() {

            actionProgressDialog.setMessage("Gaunamos kategorijos");
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
            //Kaip ir veikia
        }

        protected void onProgressUpdate(Void... progress){

        }

        protected void onPostExecute(List<Uzrasas> result){
            Toast.makeText(KategorijosActivity.this, "OnPostEx  " , Toast.LENGTH_LONG).show();
            actionProgressDialog.cancel();
            if(result.size() != 0) {
                Toast.makeText(KategorijosActivity.this, "Rastų kategorijų skaičius: " + result.size(), Toast.LENGTH_SHORT).show();
                rodytiUzrasus(result);
            }else{

                Toast.makeText(KategorijosActivity.this, "Buvo nerasta jokių kategorijų", Toast.LENGTH_SHORT).show();
                }
        }

    }
    //----------------------------------------------------------------------------------------------







    private void rodytiUzrasus(List<Uzrasas> uzrasai) {

        KategorijosDataList = new ArrayList<HashMap<String, String>>();
        TextView kat = findViewById( R.id.textKateg);
        for (int i = 0; i < uzrasai.size(); i++) {
            Uzrasas u = uzrasai.get(i);
            HashMap<String, String> UzrasasDataMap = new HashMap<>();
            UzrasasDataMap.put("id", String.valueOf(u.ID));
            UzrasasDataMap.put("pavadinimas", u.Pavadinimas);
           UzrasasDataMap.put("spalva", u.Spalva);
            KategorijosDataList.add(UzrasasDataMap);

        }

        ListView mlv = (ListView) findViewById(R.id.listKateg);

        SimpleAdapter SimpleMiestaiAdapter = new SimpleAdapter(this, KategorijosDataList, R.layout.kategorijos,
                new String[]{"pavadinimas"},
                new int[]{R.id.textKateg});

        mlv.setAdapter(SimpleMiestaiAdapter);
        mlv.setOnItemClickListener(this);
    }
}
