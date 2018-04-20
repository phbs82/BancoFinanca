package com.example.pedrobraga.bancofinanca.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


@Entity(foreignKeys = @ForeignKey(entity = Local.class,
        parentColumns = "codigolocal",
        childColumns = "codigolocal"))
public class Compra {


    @PrimaryKey(autoGenerate = true)
    private int codigocompra;

    private int codigolocal;

    private Date data;








}
