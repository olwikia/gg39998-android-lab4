package com.example.gg39998_android_lab4;

import java.io.Serializable;

public class Animal implements Serializable {

    private int _id;
    private String gatunek;
    private String kolor;
    private float wielkosc;
    private String opis;

    public Animal () {}

    public Animal(String gtunek, String kolor, float wielkosc, String opis){
        this.gatunek = gatunek;
        this.kolor  = kolor;
        this.wielkosc = wielkosc;
        this.opis = opis;
    }
    @Override
    public String toString(){
        return "Zwierze: [_id=" + _id +", gatunek=" + gatunek +", kolor=" + kolor + ", wielkosc=" + wielkosc +"]";
    }
    public String getOpis() {return opis;}
    public float getWielkosc() {return wielkosc;}
    public String getGatunek(){return gatunek;}
    public String getKolor(){return kolor;}
    public int getId(){return _id;}
    public void setId(int _id){this._id=_id;}

}
