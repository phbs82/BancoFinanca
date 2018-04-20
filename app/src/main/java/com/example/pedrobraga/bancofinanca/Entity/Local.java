package com.example.pedrobraga.bancofinanca.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by pedro.braga on 19/04/2018.
 */


@Entity
public class Local {

    @PrimaryKey(autoGenerate = true)
    private int codigolocal;


    private String desclocal;

    private float latitude;
    private float longitude;
    private String endereco;


    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getCodigolocal() {
        return codigolocal;
    }

    public void setCodigolocal(int codigolocal) {
        this.codigolocal = codigolocal;
    }

    public String getDesclocal() {
        return desclocal;
    }

    public void setDesclocal(String desclocal) {
        this.desclocal = desclocal;
    }
}
