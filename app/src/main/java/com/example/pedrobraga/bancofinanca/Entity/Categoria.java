package com.example.pedrobraga.bancofinanca.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.pedrobraga.bancofinanca.Utils.DateTypeConverter;

/**
 * Created by pedro.braga on 17/09/2018.
 */


@Entity(foreignKeys = {@ForeignKey(entity = Local.class,
        parentColumns = "codigocategoria",
        childColumns = "codigocategoria")},
        indices = {@Index(value = {"codigolocal"})}
)
public class Categoria {



    @PrimaryKey(autoGenerate = true)
    private int codigocategoria;

    private String descricaocategoria;



}
