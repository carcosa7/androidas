package com.example.lab2;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DataAPI {

    public static  final String TAG = "DataAPI";

    public static List<Uzrasas> gautiUzrasus(String RestURL) throws Exception
    {
        List<Uzrasas> uzrasai = new ArrayList<Uzrasas>();
        String response = WebAPI.gautiUzrasus(RestURL);

        if(response.length() > 1)
        {
            Gson gson;
            gson = new Gson();

            java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<Uzrasas>>() {}.getType();
            uzrasai = gson.fromJson(response, type);

        }

        return  uzrasai;
    }

    public static List<Uzrasas>  gautiPasirinktus(String RestURL, String data) throws Exception
    {
//        System.out.print("RESPONSEEEEEEE: "); // i gautiPasirinktus ateina
        List<Uzrasas> uzrasai = new ArrayList<>();

        String response = WebAPI.gautiPasirinktusWeb(RestURL, data);

       // System.out.print("RESPONSEEEEEEE3: "); // Veikia
        if(response.length() > 1)
        {
            Gson gson;
            gson = new Gson();

            java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<Uzrasas>>(){}.getType();
            uzrasai = gson.fromJson(response, type);

        }
       // System.out.print("RESPONSEEEEEEE8: ");veikia
        return uzrasai;
    }

    public static void detiNote(String url, String pav, String kat, String tekstas) throws Exception{

        WebAPI.detiUzrasa(url,pav,kat,tekstas);

    }
    public static void trintiUzrasus(String url, String ID) throws Exception{

        WebAPI.TrintiUzrasa(url,ID);

    }

    public static String Redaguoti(String RestURL, String IDis) throws Exception{
        return WebAPI.RedaguotiUzrasas(RestURL, IDis);
    }

    public static void ArSakityta(String RestURL, String ID,String busena) throws Exception{
         WebAPI.ArSkaityta(RestURL, ID,busena);
    }


}
