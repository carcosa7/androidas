package com.example.lab2;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class WebAPI extends AppCompatActivity {
    private static final String TAG = "WebAPI";

    public static String gautiUzrasus(String url) throws Exception
    {


        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("Responsse code :: " + responseCode);
        if(responseCode == HttpURLConnection.HTTP_OK) { // connection ok

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();


            while ((inputLine = in.readLine()) != null) {

                response.append(inputLine);

            }
            in.close();

            return response.toString();
        }

        return null;
    }
    public static String gautiPasirinktusWeb(String url, String data)throws  Exception {
        String bendras = url + "" + data;

       System.out.print("VEIKIAAAAAAAAAAAAAA5: " + bendras); // nespausdina
        URL obj = new URL(bendras);
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();

        StringBuffer response = new StringBuffer();

        System.out.println("Response Code :: " + responseCode);// po sito nespausdina, komentavimas nepadeda

        if (responseCode == HttpURLConnection.HTTP_OK) { //connection ok res = 200 ir Htt = 200

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }
        con.disconnect();

        return response.toString();

    }
    public static void TrintiUzrasa(String url, String ID)throws  Exception {
        String bendras = url + "" + ID;

        System.out.print("VEIKIAAAAAAAAAAAAAA5: " + bendras);
        URL obj = new URL(bendras);
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setConnectTimeout(10 * 1000);
        con.setReadTimeout(10 * 1000);
        con.setRequestMethod("GET");

       int responseCode = con.getResponseCode();

       con.disconnect();
    }

    public  static String RedaguotiUzrasas(String url, String id) throws Exception{
        String bendras = url + "" + id;
        URL obj = new URL(bendras);
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestMethod("POST");
        int responseCode = con.getResponseCode();
        StringBuffer response = new StringBuffer();
        System.out.println("Response Code ::: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { //connection ok
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }
        con.disconnect();
        return response.toString();
    }


    public static void detiUzrasa(String url, String pav, String kat, String tekstas) throws Exception {

        String data = "{\"pav\":\""+pav+"\", \"kat1\":\""+kat+"\", \"tekstas\":\""+tekstas+"\"}"; //data to post

        Log.d("JSON", data);

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MEDIA_TYPE, data);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", "application/json")
                .build();



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String mMessage = response.body().string();
                Log.e(TAG, mMessage);
            }
        });

   }
    public static void ArSkaityta(String url, String id, String busena)throws  Exception {

        String bendras = url + "" + id + "/" + busena;
        System.out.println(bendras);
        URL obj = new URL(bendras);
        System.out.println(obj.toString());
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        //con.disconnect();
    }

    public static void trintiKateg( String URL,String kateg) throws IOException {

//        URL obj = new URL("http://10.0.2.2:3000/trinti/" + sk);
        URL obj = new URL(URL + kateg);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        if(responseCode == HttpsURLConnection.HTTP_OK) {

            return;
        }
    }

}
