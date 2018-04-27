package com.example.pedrobraga.bancofinanca.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.pedrobraga.bancofinanca.Utils.DateTypeConverter;

import java.util.Date;


@Entity(foreignKeys = {@ForeignKey(entity = Local.class,
        parentColumns = "codigolocal",
        childColumns = "codigolocal")},
        indices = {@Index(value = {"codigolocal"})}
)
@TypeConverters(DateTypeConverter.class)
public class Compra {


    @PrimaryKey(autoGenerate = true)
    private int codigocompra;

    private int codigolocal;



    private Date data;


    public int getCodigocompra() {
        return codigocompra;
    }

    public void setCodigocompra(int codigocompra) {
        this.codigocompra = codigocompra;
    }

    public int getCodigolocal() {
        return codigolocal;
    }

    public void setCodigolocal(int codigolocal) {
        this.codigolocal = codigolocal;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
