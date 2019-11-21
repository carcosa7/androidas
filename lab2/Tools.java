package com.example.lab2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Tools {
    public static String RestURL = "http://10.0.2.2:3100/android/getAllNotes";
    public static String NextURL = "http://10.0.2.2:3100/android/getSelectedNotes/";
    public static String KategorijaURL = "http://10.0.2.2:3100/android/getCategories/";
    public static String IterptiURL = "http://10.0.2.2:3100/android/postNote/";
    public static String RusDidejanciaiURL = "http://10.0.2.2:3100/android/rusiotDidejanciai/";
    public static String RusMazejanciaiURL = "http://10.0.2.2:3100/android/rusiotMazejanciai/";
    public static String TrintiURL = "http://10.0.2.2:3100/android/DelNote/";
    public static String RedaguotiURL = "http://10.0.2.2:3100/android/edit/";
    public static String SkaitytaURL = "http://10.0.2.2:3100/android/ArSkaityta/";
    public static String PaieskaURL = "http://10.0.2.2:3100/android/getSelectedNotes_Pavad/";
    public static String TrintKategURL = "http://10.0.2.2:3100/android/trintiKateg/";


    public static boolean IsOnline(Context nContext)
    {
        boolean connected = false;

        try
        {
            ConnectivityManager connectivityManager = (ConnectivityManager)nContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        }
        catch (Exception e)
        {
            connected = false;
        }
        return connected;
    }


}
