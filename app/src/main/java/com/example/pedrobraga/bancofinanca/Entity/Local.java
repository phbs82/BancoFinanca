package com.example.pedrobraga.bancofinanca.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by pedro.braga on 19/04/2018.
 */


@Entity
public class Local {

    @PrimaryKey(autoGenerate = true)
    private int codigolocal=0;


    private String desclocal;

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
