package com.example.lab2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Iterpti_IrasaActivity extends Activity  {

    private static final String TAG = "Iterpti_IrasaActivity";

    private Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.irasui_iterpti);
        postButton = findViewById(R.id.btnIterpti);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postNote();
            }
        });
    }
    private void postNote()
    {
        new detiUzrasa().execute(Tools.IterptiURL, null, null);

    }

    public class detiUzrasa extends AsyncTask<String, Void, List<Uzrasas>>
    {
        ProgressDialog actionProgressDialog = new ProgressDialog(Iterpti_IrasaActivity.this);

        @Override
        protected void onPreExecute() {
            actionProgressDialog.setMessage("Dedamas Uzrasas");
            actionProgressDialog.show();
            actionProgressDialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected List<Uzrasas> doInBackground(String... str_param) {
            String PostURL = str_param[0];

            EditText pavText = (EditText) findViewById(R.id.edPavadinimas);
            String pav = pavText.getText().toString();

            EditText katText = (EditText) findViewById(R.id.edKategorija);
            String kat = katText.getText().toString();

            EditText tekstasText = (EditText) findViewById(R.id.edTekstas);
            String tekst = tekstasText.getText().toString();

            try {
                DataAPI.detiNote(PostURL,pav,kat,tekst);
            } catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
            return null;
        }

        protected void onProgressUpdate(Void... progress) {
        }

        protected void onPostExecute(List<Uzrasas> result) {
            actionProgressDialog.cancel();
            Toast.makeText(Iterpti_IrasaActivity.this, "Uzrasas iterptas", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Iterpti_IrasaActivity.this, RusiavimasActivity.class);
            intent.putExtra("Rusiavimo_Tipas",  "Didejanciai");
            startActivity(intent);
            //return;
        }
    }
}


