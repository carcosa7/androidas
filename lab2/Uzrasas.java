package com.example.lab2;

public class Uzrasas {

    public int ID;
    public String Pavadinimas;
    public String DataIrLaikas;
    public String Spalva;
    public String Kategorija;
    public String Tekstas;
    public String Perziureta;

    public Uzrasas() {

    }

    public Uzrasas(int ID, String pavadinimas, String dataIrLaikas, String spalva, String kategorija, String tekstas,String perziuretas) {
        this.ID = ID;
        Pavadinimas = pavadinimas;
        DataIrLaikas = dataIrLaikas;
        Spalva = spalva;
        Kategorija = kategorija;
        Tekstas = tekstas;
        Perziureta = perziuretas;
    }

}
